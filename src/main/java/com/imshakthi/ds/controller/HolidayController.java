package com.imshakthi.ds.controller;

import com.imshakthi.ds.dto.response.LastCelebratedHolidaysResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayController {

  /**
   * Given a country, return the last celebrated 3 holidays (date and name).
   *
   * @param country ISO code of the country.
   * @return list of celebrated holidays for given country.
   */
  @Operation(summary = "Given a country, return the last celebrated 3 holidays (date and name)")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of last celebrated 3 holidays.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LastCelebratedHolidaysResponse.class))
            }),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
      })
  @GetMapping("/last-celebrated-holidays/{country}")
  public ResponseEntity<LastCelebratedHolidaysResponse> getLastCelebratedHolidays(
      final @PathVariable("country") String country) {
    return ResponseEntity.ok(new LastCelebratedHolidaysResponse());
  }

  // Given a year and country codes, for each country return a number of public holidays not falling
  // on weekends (sort in descending order).

  // Given a year and 2 country codes, return the deduplicated list of dates celebrated in both
  // countries (date + local names)

}
