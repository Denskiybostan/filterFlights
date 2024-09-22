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
//    public static List<Flight> filterFlightsWhereTimeSpentOnGroundMoreThanTwoHoursTests(List<Flight> setFlights) {
//
//        return setFlights.stream()
//                .forEach(Flight::getSegments)
//
//                .filter(flight -> {
//
//                    Duration totalGroundTime = Duration.ZERO;
//                    List<Segment> segments = flight.getSegments();
//                    for (int i = 1; i < segments.size(); i++) {
//                        LocalDateTime timeDeparture = segments.get(i).getDepartureDate();
//                        LocalDateTime timeArrival = segments.get(i - 1).getArrivalDate();
//                        Duration groundTime = Duration.between(timeArrival, timeDeparture);
//                        totalGroundTime = totalGroundTime.plus(groundTime);
//                    }
//                    return totalGroundTime.toHours() < 2;
//                })
//                .collect(Collectors.toList());
//    }

//public static List<Flight> filterFlightsWhereTimeSpentOnGroundMoreThanTwoHours(List<Flight> setFlights) {
//    List<Flight> filteredFlights = new ArrayList<>();
//    Duration totalGroundTime = Duration.ZERO;
//    for (Flight flight : setFlights) {
//        List<Segment> segments = flight.getSegments();
//        for (int i = 1; i < segments.size(); i++) {
//            LocalDateTime timeDeparture = segments.get(i).getDepartureDate();
//            LocalDateTime timeArrival = segments.get(i - 1).getArrivalDate();
//
//            Duration groundTime = Duration.between(timeArrival, timeDeparture);
//            totalGroundTime = totalGroundTime.plus(groundTime);
//        }
//        if (totalGroundTime.toHours() > 2) {
//            filteredFlights.add(flight);
//        }
//        totalGroundTime = Duration.ZERO;
//    }
//    return filteredFlights;
//}

//    private static List<Flight> flightsFilterArrivalBeforeDeparture(List<Flight> initialFlights) {
//        List<Flight> filteredFlights = new ArrayList<>();
//        for (Flight flight : initialFlights) {
//            boolean isSegmentIncorrect = false;
//            for (Segment segment : flight.getSegments()) {
//                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
//                    isSegmentIncorrect = true;
//                }
//            }
//            if (!isSegmentIncorrect) {
//                filteredFlights.add(flight);
//            }
//        }
//        System.out.println(filteredFlights);
//        return filteredFlights;
//    }


//
//    private static List<Flight> flightsFilterWaitingMoreThenTwoHours(List<Flight> initialFlights) {
//        List<Flight> filteredFlights = new ArrayList<>();
//        for (Flight flight : initialFlights) {
//            boolean isSegmentIncorrect = false;
//            Segment previousSegment = null;
//            LocalDateTime waitingTime = null;
//            for (Segment segment : flight.getSegments()) {
//                waitingTime = segment.getDepartureDate();
//                    if (previousSegment != null && waitingTime != null) {
//
//                        if (segment.getDepartureDate().isBefore(waitingTime)) {
//                            isSegmentIncorrect = true;
//                            break;
//                        }
//                    }
//                previousSegment = segment;
//            }
//            if (!isSegmentIncorrect) {
//                filteredFlights.add(flight);
//            }
//            System.out.println(filteredFlights);
//        }
//        return filteredFlights;
//    }
//}




//    private static List<Flight> flightsFilterWaitingMoreThenTwoHours1 (List<Flight> initialFlights) {
//        List<Flight> filteredFlights = new ArrayList<>();
//        for(Flight flight : initialFlights){
//            boolean isSegmentIncorrect = false;
//            Segment previousSegment=null;
//            for (Segment segment : flight.getSegments()){
//                if (previousSegment != null) {
//                    if (segment.getDepartureDate().isAfter(previousSegment.getArrivalDate().plusHours(2))) {
//                        isSegmentIncorrect = true;
//                    }
//                }
//                previousSegment = segment;
//            }
//            if (!isSegmentIncorrect) {
//                filteredFlights.add(flight);
//            }
//        }
//        System.out.println(filteredFlights);
//        return filteredFlights;
//    }
//public static List<Flight> flightsFilterDepartureTime(List<Flight> initialFlights) {
//        List<Flight> filteredFlights = new ArrayList<>(); //создание пустого массива с последующей фильтрацией по указанному признаку
//        for (Flight flight : initialFlights) {
//            boolean isSegmentIncorrect = false;
//            for (Segment segment : flight.getSegments()) {
//                if (segment.getDepartureDate().isBefore(LocalDateTime.now())) {
//                    isSegmentIncorrect = true;
//                }
//            }
//            if (!isSegmentIncorrect) {
//                filteredFlights.add(flight);
//            }
//        }
//        System.out.println(filteredFlights);
//        return filteredFlights;
//    }

