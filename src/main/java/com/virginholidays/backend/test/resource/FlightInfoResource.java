package com.virginholidays.backend.test.resource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.regex.Pattern;

import javax.validation.constraints.NotEmpty;

import org.springframework.http.CacheControl;
import static org.springframework.http.CacheControl.noCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.status;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virginholidays.backend.test.api.Flight;
import com.virginholidays.backend.test.models.Response;
import com.virginholidays.backend.test.service.FlightInfoService;

/**
 * @author Geoff Perks
 *
 *         The FlightInfoResource
 */
@RestController
public class FlightInfoResource {
    private final FlightInfoService flightInfoService;
    private static final Pattern isoDateTimePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}");

    /**
     * The constructor
     *
     * @param flightInfoService the flightInfoService
     */
    public FlightInfoResource(FlightInfoService flightInfoService) {
        this.flightInfoService = flightInfoService;
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
    private <T> CompletableFuture<ResponseEntity<Response<T>>> errorResponse(HttpStatus httpStatus, String message) {
        return CompletableFuture.completedFuture(
                ResponseEntity.status(
                        httpStatus).cacheControl(CacheControl.noCache())
                        .body(new Response<>(httpStatus.value(), message, null)));
    }

    /**
     * Resource method for returning flight results
     *
     * @param date the chosen date
     * @return flights for the day of the chosen date
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{date}/results")
    public CompletionStage<ResponseEntity<Response<List<Flight>>>> getResults(
            @PathVariable("date") @NotEmpty String date) {
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

            return flightInfoService.findFlightByDayOfWeek(dayOfWeek).thenApply(maybeResults -> {

                // no results, no content
                if (maybeResults.isEmpty()) {
                    return status(HttpStatus.NO_CONTENT).cacheControl(noCache()).build();
                }

                List<Flight> results = maybeResults.get();

                return status(HttpStatus.OK).cacheControl(noCache())
                        .body(new Response<List<Flight>>(HttpStatus.OK.value(), "Successfully gotten records",
                                results));
            });
        } catch (Exception e) {
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return CompletableFuture.completedFuture(status(
                    HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(httpStatus.value(), "An unfortunate error has occurred", null)));
        }
    }
}
