package com.imshakthi.ds.controller;

import com.imshakthi.ds.dto.response.CountriesAndPublicHolidaysResponse;
import com.imshakthi.ds.dto.response.Holiday;
import com.imshakthi.ds.dto.response.LastCelebratedHolidaysResponse;
import com.imshakthi.ds.mapper.HolidayMapper;
import com.imshakthi.ds.service.CountryService;
import com.imshakthi.ds.service.HolidayService;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/holidays")
@Slf4j
public class HolidayControllerImpl implements HolidayController {

  private final CountryService countryService;
  private final HolidayService holidayService;
  private final HolidayMapper holidayMapper;

  @Override
  public ResponseEntity<LastCelebratedHolidaysResponse> getLastCelebratedHolidays(
      final @PathVariable("country") String countryCode) {

    if (!countryService.isValidCountryCode(countryCode)) {
      log.error("invalid country code: {}", countryCode);
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    final var holidays = holidayService.getLastCelebratedHolidays(countryCode);

    return ResponseEntity.ok(new LastCelebratedHolidaysResponse(holidayMapper.map(holidays)));
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
