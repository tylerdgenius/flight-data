package com.virginholidays.backend.test.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import com.virginholidays.backend.test.api.Flight;

/**
 * The FlightInfoService
 *
 * @author Geoff Perks
 */
public interface FlightInfoService {

    /**
     * Finds flights for any date.
     *
     * @param outboundDate the outbound departure date
     * @return optional flights.
     */
    CompletionStage<Optional<List<Flight>>> findFlightByDayOfWeek(DayOfWeek dayOfWeek);
}
