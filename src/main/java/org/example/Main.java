package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Изначальные полеты\n"+ flights);

        System.out.println("\nБез вылетов до текущей даты\n"+ flightsFilterDepartureTime(flights));
        System.out.println("\nБез прилетов до вылета\n"+ flightsFilterArrivalBeforeDeparture(flights));
        System.out.println("\nБез полетов с ожиданием больше двух часов\n"+ flightsFilterWaitingMoreThenTwoHours(flights));
    }

    public static List<Flight> flightsFilterDepartureTime (List<Flight> initialFlights) {
        List<Flight> filteredFlights = new ArrayList<>(); //создание пустого массива с последующей фильтрацией по указанному признаку
        for(Flight flight : initialFlights){
            boolean isSegmentIncorrect = false;
            for (Segment segment : flight.getSegments()){
                if (segment.getDepartureDate().isBefore(LocalDateTime.now())) {
                    isSegmentIncorrect = true;
                }
            }
            if (!isSegmentIncorrect) {
                filteredFlights.add(flight);
            }

        }
        System.out.println(filteredFlights);
        return filteredFlights;

    }
    private static List<Flight> flightsFilterArrivalBeforeDeparture (List<Flight> initialFlights) {
        List<Flight> filteredFlights = new ArrayList<>();
        for(Flight flight : initialFlights){
            boolean isSegmentIncorrect = false;
            for (Segment segment : flight.getSegments()){
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    isSegmentIncorrect = true;
                }
            }
            if (!isSegmentIncorrect) {
                filteredFlights.add(flight);
            }

        }
        System.out.println(filteredFlights);
        return filteredFlights;
    }
    private static List<Flight> flightsFilterWaitingMoreThenTwoHours (List<Flight> initialFlights) {
        List<Flight> filteredFlights = new ArrayList<>();
        for(Flight flight : initialFlights){
            boolean isSegmentIncorrect = false;
            Segment previousSegment=null;
            for (Segment segment : flight.getSegments()){
                if (previousSegment != null) {
                    if (segment.getDepartureDate().isAfter(previousSegment.getArrivalDate().plusHours(2))) {
                        isSegmentIncorrect = true;
                    }
                }
                previousSegment = segment;
            }
            if (!isSegmentIncorrect) {
                filteredFlights.add(flight);
            }
        }
        System.out.println(filteredFlights);
        return filteredFlights;
    }
}