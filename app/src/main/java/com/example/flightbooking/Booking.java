package com.example.flightbooking;

public class Booking {
    private String airline;
    private String arrivalTime;
    private String departureTime;
    private String userId;

    public Booking() {
        // Default constructor required for Firebase
    }

    public Booking(String airline, String arrivalTime, String departureTime, String userId) {
        this.airline = airline;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.userId = userId;
    }

    public String getAirline() {
        return airline;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getUserId() {
        return userId;
    }
}