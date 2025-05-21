package com.virginholidays.backend.test.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.repository.FlightInfoRepository;

/**
 * The service implementation of FlightInfoService
 *
 * @author Geoff Perks
 */
@Service
public class FlightInfoServiceImpl implements FlightInfoService {

    private static final Pattern isoDateTimePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}");

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
     * Error response method to return a response entity with an error message
     * 
     * @param <T>     Generic type that can change based on the response. Defaults
     *                to null
     * @param status  integer value to signify status code
     * @param message the error message
     * @return
     */
    private <T> CompletableFuture<Optional<List<Flight>>> errorResponse(HttpStatus httpStatus, String message) {
        throw new HttpClientErrorException(httpStatus, message);
    }

    /**
     * Finds flights for any date by filtering the flights based on the day of the
     * week
     * 
     * @param dayOfWeek the day of the week to filter the flights for
     */
    @Override
    public CompletionStage<Optional<List<Flight>>> findFlightByDate(
            String date) {

        if (date == null || date.isEmpty()) {
            return errorResponse(HttpStatus.BAD_REQUEST, "Date cannot be null or empty.");
        }

        if (!isoDateTimePattern.matcher(date).matches()) {
            return errorResponse(HttpStatus.BAD_REQUEST,
                    "Invalid date format. Expected ISO 8601 format like '2024-05-21'");
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);

            DayOfWeek dayOfWeek = parsedDate.getDayOfWeek();

            CompletionStage<Optional<List<Flight>>> flightsResult = flightInfoRepository.findAll()
                    .thenApply(optionalFlights -> optionalFlights.map(flights -> flights.stream()
                            .filter(flight -> flight.days().contains(
                                    dayOfWeek))
                            .collect(Collectors.toList())));

            return flightsResult;

        } catch (Exception e) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unfortunate error has occurred");
        }

    }
}
