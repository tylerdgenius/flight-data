package com.virginholidays.backend.test.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.repository.FlightInfoRepository;

/**
 * The service implementation of FlightInfoService
 *
 * @author Geoff Perks
 */
@Service
public class FlightInfoServiceImpl implements FlightInfoService {

    private final FlightInfoRepository flightInfoRepository;

    /**
     * The constructor
     *
     * @param flightInfoRepository the flightInfoRepository
     */
    public FlightInfoServiceImpl(FlightInfoRepository flightInfoRepository) {
        this.flightInfoRepository = flightInfoRepository;
    }

    /**
     * Finds flights for any date by filtering the flights based on the day of the
     * week
     * 
     * @param dayOfWeek the day of the week to filter the flights for
     */
    @Override
    public CompletionStage<Optional<List<Flight>>> findFlightByDayOfWeek(
            DayOfWeek dayOfWeek) {

        System.out.println(dayOfWeek);

        return flightInfoRepository.findAll()
                .thenApply(optionalFlights -> optionalFlights.map(flights -> flights.stream()
                        .filter(flight -> flight.days().contains(
                                dayOfWeek))
                        .collect(Collectors.toList())));
    }
}
