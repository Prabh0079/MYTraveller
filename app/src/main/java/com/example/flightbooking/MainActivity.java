package com.example.flightbooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Spinner departureSpinner, arrivalSpinner;
    private EditText date;
    private Button profile_button, booking_button, adultsMinus, adultsPlus, childrenMinus, childrenPlus, searchButton;
    private TextView adultsCount, childrenCount, userNameTextView;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;

    private final Map<String, String> iataMap = new HashMap<String, String>() {{
        put("Auckland International Airport", "AKL");
        put("Kuala Lumpur International Airport", "KUL");
        put("Sydney Kingsford Smith Airport", "SYD");
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        departureSpinner = findViewById(R.id.departure_spinner);
        arrivalSpinner = findViewById(R.id.arrival_spinner);
        date = findViewById(R.id.date);
        adultsMinus = findViewById(R.id.adults_minus);
        adultsPlus = findViewById(R.id.adults_plus);
        childrenMinus = findViewById(R.id.children_minus);
        childrenPlus = findViewById(R.id.children_plus);
        adultsCount = findViewById(R.id.adults_count);
        childrenCount = findViewById(R.id.children_count);
        searchButton = findViewById(R.id.search_button);
        profile_button = findViewById(R.id.profile_button);
        booking_button = findViewById(R.id.booking_button);
        userNameTextView = findViewById(R.id.userNameTextView);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);


        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        booking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BookingActivity.class));
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                iataMap.keySet().toArray(new String[0])
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureSpinner.setAdapter(adapter);
        arrivalSpinner.setAdapter(adapter);
        date.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, day) -> date.setText(day + "/" + (month + 1) + "/" + year),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );


            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


            datePickerDialog.show();
        });


        adultsPlus.setOnClickListener(v -> {
            int count = Integer.parseInt(adultsCount.getText().toString());
            adultsCount.setText(String.valueOf(++count));
        });

        adultsMinus.setOnClickListener(v -> {
            int count = Integer.parseInt(adultsCount.getText().toString());
            if (count > 1) adultsCount.setText(String.valueOf(--count));
        });

        childrenPlus.setOnClickListener(v -> {
            int count = Integer.parseInt(childrenCount.getText().toString());
            childrenCount.setText(String.valueOf(++count));
        });

        childrenMinus.setOnClickListener(v -> {
            int count = Integer.parseInt(childrenCount.getText().toString());
            if (count > 0) childrenCount.setText(String.valueOf(--count));
        });

        searchButton.setOnClickListener(v -> {
            String departureCode = iataMap.get(departureSpinner.getSelectedItem().toString());
            String arrivalCode = iataMap.get(arrivalSpinner.getSelectedItem().toString());
            String flightDate = date.getText().toString();
            int adults = Integer.parseInt(adultsCount.getText().toString());
            int children = Integer.parseInt(childrenCount.getText().toString());

            if (departureCode.equals(arrivalCode)) {
                Toast.makeText(this, "Departure and Arrival cannot be the same", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            intent.putExtra("departure", departureCode);
            intent.putExtra("arrival", arrivalCode);
            intent.putExtra("date", flightDate);
            intent.putExtra("adults", adults);
            intent.putExtra("children", children);
            startActivity(intent);
        });

        loadUserData();
    }

    private void loadUserData() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);

                    userNameTextView.setText(name != null ? "Hi " + name : "User");
                } else {
                    Toast.makeText(MainActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load user details", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
