package com.virginholidays.backend.test.resource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.validation.constraints.NotEmpty;

import static org.springframework.http.CacheControl.noCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.status;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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

    /**
     * The constructor
     *
     * @param flightInfoService the flightInfoService
     */
    public FlightInfoResource(FlightInfoService flightInfoService) {
        this.flightInfoService = flightInfoService;
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
        try {
            return flightInfoService.findFlightByDate(date).thenApply(maybeResults -> {

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

            if (e instanceof HttpClientErrorException || e instanceof NullPointerException
                    || e instanceof IllegalArgumentException) {
                httpStatus = HttpStatus.BAD_REQUEST;
            }

            return CompletableFuture.completedFuture(status(
                    HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(httpStatus.value(), "An unfortunate error has occurred", null)));
        }
    }
}
