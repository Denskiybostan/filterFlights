package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Изначальные полеты\n" + flights);

        System.out.println("\nБез вылетов до текущей даты\n" + flightsFilterDepartureTime(flights));
        System.out.println("\nБез прилетов до вылета\n" + flightsFilterArrivalBeforeDeparture(flights));
        System.out.println("\nБез полетов с ожиданием больше двух часов\n" + filterFlightsWhereTimeSpentOnGroundMoreThanTwoHours(flights));
    }
    public static List<Flight> flightsFilterDepartureTime(List<Flight> initialFlights) {
        return initialFlights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate().isBefore(LocalDateTime.now())))
                .collect(Collectors.toList());
    }
    public static List<Flight> flightsFilterArrivalBeforeDeparture(List<Flight> initialFlights) {
        return initialFlights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    public static List<Flight> filterFlightsWhereTimeSpentOnGroundMoreThanTwoHours(List<Flight> setFlights) {
        return setFlights.stream()
                .filter(flight -> methodFilter(flight).toHours() <= 2)
                .collect(Collectors.toList());
    }


    private static Duration methodFilter(Flight flight) {
        List<Segment> segments = flight.getSegments();
        Duration timeWait = Duration.ZERO;
        for (int i = 1; i < segments.size(); i++) {
            LocalDateTime timeDeparture = segments.get(i).getDepartureDate();
            LocalDateTime timeArrival = segments.get(i - 1).getArrivalDate();
            Duration groundTime = Duration.between(timeArrival, timeDeparture);
            timeWait = timeWait.plus(groundTime);
        }
        return timeWait;
    }
    }
