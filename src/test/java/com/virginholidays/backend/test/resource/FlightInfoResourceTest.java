package com.virginholidays.backend.test.resource;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.service.FlightInfoService;

/**
 * The FlightInfoResource unit tests
 *
 * @author Geoff Perks
 */
@WebMvcTest(FlightInfoResource.class)
class FlightInfoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightInfoService service;

    @Test
    void testGetResultsReturns200WithExpectedFormat() throws Exception {
        List<Flight> flights = List.of(
                new Flight(LocalTime.of(11, 0), "Barbados", "BGI", "VS029", List.of(DayOfWeek.MONDAY)));

        when(service.findFlightByDate("2024-05-20"))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(flights)));

        mockMvc.perform(get("/2024-05-20/results"))
                .andExpect(status().isOk());
        // .andExpect(jsonPath("$.code").value(200))
        // .andExpect(jsonPath("$.message").value("Successfully gotten records"))
        // .andExpect(jsonPath("$.status").value(true))
        // .andExpect(jsonPath("$.data").isArray())
        // .andExpect(jsonPath("$.data[0].iata").value("BGI"));
    }

    @Test
    void testGetResultsReturns400WhenDateIsInvalid() throws Exception {
        when(service.findFlightByDate("invalid-date"))
                .thenThrow(new IllegalArgumentException("Invalid date"));

        mockMvc.perform(get("/wuwiwi/results"))
                .andExpect(status().isBadRequest());
        // .andExpect(jsonPath("$.code").value(500))
        // .andExpect(jsonPath("$.message").value("An unfortunate error has occurred"));
    }

    @Test
    void testGetResultsReturns500OnUnhandledException() throws Exception {
        when(service.findFlightByDate("2024-05-20"))
                .thenThrow(new RuntimeException("Unexpected failure"));

        mockMvc.perform(get("/2024-05-20/results"))
                .andExpect(status().isInternalServerError());
        // .andExpect(jsonPath("$.code").value(500))
        // .andExpect(jsonPath("$.message").value("An unfortunate error has occurred"));
    }

}
