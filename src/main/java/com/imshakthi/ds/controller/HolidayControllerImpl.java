package com.imshakthi.ds.controller;

import com.imshakthi.ds.dto.response.CountriesAndPublicHolidaysResponse;
import com.imshakthi.ds.dto.response.Holiday;
import com.imshakthi.ds.dto.response.LastCelebratedHolidaysResponse;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayControllerImpl implements HolidayController {

  @Override
  public ResponseEntity<LastCelebratedHolidaysResponse> getLastCelebratedHolidays(
      final @PathVariable("country") String country) {
    return ResponseEntity.ok(new LastCelebratedHolidaysResponse(List.of()));
  }

  @Override
  public ResponseEntity<CountriesAndPublicHolidaysResponse> getPublicHolidays(
      final @PathVariable("year") int year,
      final @RequestParam(value = "countryCodes") @NotEmpty List<String> countries) {

    return ResponseEntity.ok(new CountriesAndPublicHolidaysResponse(List.of()));
  }

  @Override
  public ResponseEntity<List<Holiday>> getHolidays(
      final @PathVariable("year") int year,
      final @RequestParam(value = "countryCodes") @NotEmpty List<String> countries) {

    return ResponseEntity.ok(List.of());
  }
}
