package com.virginholidays.backend.test.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import com.virginholidays.backend.test.api.Flight;

/**
 * The FlightInfoRepository
 *
 * @author Geoff Perks
 */
public interface FlightInfoRepository {

    /**
     * Finds all the flights available
     *
     * @return an optional of all the flights
     */
    CompletionStage<Optional<List<Flight>>> findAll();
}
