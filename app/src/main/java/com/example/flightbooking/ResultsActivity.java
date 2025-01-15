package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress_bar);
        back_button = findViewById(R.id.back_button);




        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        String departure = getIntent().getStringExtra("departure");
        String arrival = getIntent().getStringExtra("arrival");
        String date = formatDate(getIntent().getStringExtra("date"));

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.aviationstack.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AviationstackApi api = retrofit.create(AviationstackApi.class);
        api.getFlights("77378f925da40dbb2e02b8bdd8b1d9ec", departure, arrival, date)
                .enqueue(new Callback<FlightResponse>() {
                    @Override
                    public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                         progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {
                            List<FlightResponse.FlightData> flights = response.body().getData();

                            Log.d("FlightResponse", "Flight data: " + flights.toString());
                            Log.d("FlightResponse", "Flights received: " + flights.size());

                            List<FlightResponse.FlightData> scheduledFlights = new ArrayList<>();
                            for (FlightResponse.FlightData flight : flights) {
                                if ("scheduled".equals(flight.getFlightStatus())) {
                                    scheduledFlights.add(flight);
                                }
                            }

                            if (scheduledFlights.isEmpty()) {
                                Toast.makeText(ResultsActivity.this, "No scheduled flights available", Toast.LENGTH_SHORT).show();
                            }

                            FlightAdapter adapter = new FlightAdapter(scheduledFlights);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Toast.makeText(ResultsActivity.this, "No flights available", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FlightResponse> call, Throwable t) {
                         progressBar.setVisibility(View.GONE);
                        Toast.makeText(ResultsActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputFormat.parse(dateString);

            SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            return outputFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            return dateString;
        }
    }
}

