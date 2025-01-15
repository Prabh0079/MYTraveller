package com.example.flightbooking;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private List<FlightResponse.FlightData> flights;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    public FlightAdapter(List<FlightResponse.FlightData> flightDataList) {
        this.flights = flightDataList;

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("bookings");
    }


    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        FlightResponse.FlightData flightData = flights.get(position);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy MMM dd, hh:mm a", Locale.getDefault());


            try {
                Date departureDate = inputFormat.parse(flightData.getDeparture().getScheduled());
                Date arrivalDate = inputFormat.parse(flightData.getArrival().getScheduled());

                String formattedDepartureTime = outputFormat.format(departureDate);
                String formattedArrivalTime = outputFormat.format(arrivalDate);

                holder.airline.setText(flightData.getAirline().getName());
                holder.departureTime.setText("Departure: " + formattedDepartureTime);
                holder.arrivalTime.setText("Arrival: " + formattedArrivalTime);
                holder.flightNumber.setText("Flight No: " + flightData.getFlight().getNumber());
                holder.date.setText("Date: " + flightData.getFlightDate());
//                holder.price.setText("Status: " + flightData.getFlightStatus());

            } catch (Exception e) {
                e.printStackTrace();
            }

        holder.book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("flight", flightData.getAirline().getName());
                Date departureDate = null;
                Date arrivalDate = null;
                try {
                    departureDate = inputFormat.parse(flightData.getDeparture().getScheduled());
                    arrivalDate = inputFormat.parse(flightData.getArrival().getScheduled());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


                String formattedDepartureTime = outputFormat.format(departureDate);
                String formattedArrivalTime = outputFormat.format(arrivalDate);
                String userId = auth.getCurrentUser().getUid();
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("userId", userId);
                userMap.put("airline", flightData.getAirline().getName());
                userMap.put("flightNumber", flightData.getFlight().getNumber());
                userMap.put("departureTime", formattedDepartureTime);
                userMap.put("arrivalTime", formattedArrivalTime);


                databaseReference.child(userId).setValue(userMap)
                        .addOnCompleteListener(dbTask -> {
                            if (dbTask.isSuccessful()) {
                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("Message")
                                        .setMessage("Your flight book success, Agent will contact you shortly." )
                                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                        .show();
                            } else {
                                 new AlertDialog.Builder(view.getContext())
                                        .setTitle("Message")
                                        .setMessage("Your flight book failed, try again: " )
                                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                        .show();
                            }

                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView airline, departureTime, arrivalTime, flightNumber, date, price;
        Button book_btn;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            airline = itemView.findViewById(R.id.tv_flight_name);
            departureTime = itemView.findViewById(R.id.tv_departure);
            arrivalTime = itemView.findViewById(R.id.tv_arrival);
            flightNumber = itemView.findViewById(R.id.tv_flight_number);
            date = itemView.findViewById(R.id.tv_date);
//            price = itemView.findViewById(R.id.tv_price);
            book_btn = itemView.findViewById(R.id.btn_book_now);
        }
    }
}
