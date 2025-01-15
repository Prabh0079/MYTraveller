package com.example.flightbooking;

import java.util.List;

public class FlightResponse {
    private List<FlightData> data;

    public List<FlightData> getData() {
        return data;
    }

    public void setData(List<FlightData> data) {
        this.data = data;
    }

    public static class FlightData {
        private String flight_date;
        private String flight_status;
        private Departure departure;
        private Arrival arrival;
        private Airline airline;
        private Flight flight;

        public String getFlightDate() {
            return flight_date;
        }

        public void setFlightDate(String flight_date) {
            this.flight_date = flight_date;
        }

        public String getFlightStatus() {
            return flight_status;
        }

        public void setFlightStatus(String flight_status) {
            this.flight_status = flight_status;
        }

        public Departure getDeparture() {
            return departure;
        }

        public void setDeparture(Departure departure) {
            this.departure = departure;
        }

        public Arrival getArrival() {
            return arrival;
        }

        public void setArrival(Arrival arrival) {
            this.arrival = arrival;
        }

        public Airline getAirline() {
            return airline;
        }

        public void setAirline(Airline airline) {
            this.airline = airline;
        }

        public Flight getFlight() {
            return flight;
        }

        public void setFlight(Flight flight) {
            this.flight = flight;
        }


        public static class Departure {
            private String airport;
            private String iata;
            private String scheduled;

            public String getAirport() {
                return airport;
            }

            public void setAirport(String airport) {
                this.airport = airport;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getScheduled() {
                return scheduled;
            }

            public void setScheduled(String scheduled) {
                this.scheduled = scheduled;
            }
        }

        public static class Arrival {
            private String airport;
            private String iata;
            private String scheduled;

            public String getAirport() {
                return airport;
            }

            public void setAirport(String airport) {
                this.airport = airport;
            }

            public String getIata() {
                return iata;
            }

            public void setIata(String iata) {
                this.iata = iata;
            }

            public String getScheduled() {
                return scheduled;
            }

            public void setScheduled(String scheduled) {
                this.scheduled = scheduled;
            }
        }

        public static class Airline {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class Flight {
            private String number;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }
    }
}
