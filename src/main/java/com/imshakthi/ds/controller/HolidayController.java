package com.imshakthi.ds.controller;

import com.imshakthi.ds.dto.response.CountriesAndPublicHolidaysResponse;
import com.imshakthi.ds.dto.response.ErrorResponse;
import com.imshakthi.ds.dto.response.Holiday;
import com.imshakthi.ds.dto.response.LastCelebratedHolidaysResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/holidays")
public interface HolidayController {

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
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping("/last-celebrated/{country}")
  ResponseEntity<LastCelebratedHolidaysResponse> getLastCelebratedHolidays(
      final @PathVariable("country") String country);

  @Operation(
      summary =
          "Given a year and country codes, for each country return a number of public holidays not falling on weekends (sort in descending order).")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of public holidays for given countries.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CountriesAndPublicHolidaysResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping("/{year}/public")
  ResponseEntity<CountriesAndPublicHolidaysResponse> getPublicHolidays(
      final @PathVariable("year") int year,
      final @RequestParam(value = "countryCodes") @NotEmpty List<String> countries);

  /**
   * Get deduplicated list of dates celebrated in both countries.
   *
   * @param year year
   * @param countries list of countries
   * @return list of holidays with date and local names.
   */
  @Operation(
      summary =
          "Given a year and 2 country codes, return the deduplicated list of dates celebrated in both countries (date + local names).")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of holidays for given countries with local names.",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = Holiday.class)))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping("/{year}")
  ResponseEntity<List<Holiday>> getHolidays(
      final @PathVariable("year") int year,
      final @RequestParam(value = "country-codes") @NotEmpty List<String> countries);
}
