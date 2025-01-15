package com.example.flightbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private Context context;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.airlineTextView.setText("AirLine: "+booking.getAirline());
        holder.arrivalTimeTextView.setText("Arrival Date: "+booking.getArrivalTime());
        holder.departureTimeTextView.setText("Departure Date: " + booking.getDepartureTime());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView airlineTextView;
        TextView arrivalTimeTextView;
        TextView departureTimeTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            airlineTextView = itemView.findViewById(R.id.airline);
            arrivalTimeTextView = itemView.findViewById(R.id.arrival_time);
            departureTimeTextView = itemView.findViewById(R.id.departure_time);
        }
    }
}