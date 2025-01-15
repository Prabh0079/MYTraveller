package com.example.flightbooking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AviationstackApi {
    @GET("flights")
    Call<FlightResponse> getFlights(
            @Query("access_key") String apiKey,
            @Query("dep_iata") String departure,
            @Query("arr_iata") String arrival,
            @Query("date") String date
    );
}
