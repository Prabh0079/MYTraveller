package com.example.flightbooking;
public class Flight {
    private String airline;
    private String departureTime;
    private String arrivalTime;
    private String price;

    // Constructor
    public Flight(String airline, String departureTime, String arrivalTime, String price) {
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    // Getters
    public String getAirline() {
        return airline;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getPrice() {
        return price;
    }
}

