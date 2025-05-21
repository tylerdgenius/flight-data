package com.virginholidays.backend.test.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.repository.FlightInfoRepository;

/**
 * The FlightInfoServiceImpl unit tests
 *
 * @author Geoff Perks
 */
@ExtendWith(MockitoExtension.class)
class FlightInfoServiceImplTest {

    @Mock
    private FlightInfoRepository repository;

    @InjectMocks
    private FlightInfoServiceImpl service;

    @Test
    void testFilteringByDayOfWeek() throws Exception {
        List<Flight> mockFlights = List.of(
                new Flight(LocalTime.of(10, 0), "Lagos", "LOS", "VS001", List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)),
                new Flight(LocalTime.of(12, 0), "Dubai", "DXB", "VS002", List.of(DayOfWeek.WEDNESDAY)));

        when(repository.findAll()).thenReturn(CompletableFuture.completedFuture(Optional.of(mockFlights)));

        Optional<List<Flight>> filtered = service.findFlightByDate("2025-04-19").toCompletableFuture().get();

        assertThat(filtered.isPresent(), equalTo(true));
        assertThat(filtered.get().size(), equalTo(1));

        assertThat(filtered.get().get(0).iata(), equalTo("LOS"));
        assertThat(filtered.get().get(1).flightNo(), equalTo("VS002"));
    }
}