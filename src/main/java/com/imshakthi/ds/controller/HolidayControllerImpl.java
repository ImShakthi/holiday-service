package com.imshakthi.ds.controller;

import com.imshakthi.ds.dto.response.CountriesPublicHolidaysResponse;
import com.imshakthi.ds.dto.response.LastCelebratedHolidaysResponse;
import com.imshakthi.ds.dto.response.LocalHoliday;
import com.imshakthi.ds.exception.BadRequestException;
import com.imshakthi.ds.mapper.HolidayMapper;
import com.imshakthi.ds.service.CountryService;
import com.imshakthi.ds.service.HolidayServiceImpl;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final HolidayServiceImpl holidayService;
  private final HolidayMapper holidayMapper;

  @Override
  public ResponseEntity<LastCelebratedHolidaysResponse> getLastCelebratedHolidays(
      final @PathVariable("country") String countryCode) {

    validateCountryCode(countryCode);

    final var holidays = holidayService.getLastCelebratedHolidays(countryCode);

    return ResponseEntity.ok(new LastCelebratedHolidaysResponse(holidayMapper.map(holidays)));
  }

  @Override
  public ResponseEntity<CountriesPublicHolidaysResponse> getPublicHolidays(
      final @PathVariable("year") int year,
      final @RequestParam(value = "countryCodes") @NotEmpty List<String> countryCodes) {

    countryCodes.forEach(this::validateCountryCode);

    final var nonWeekendPublicHolidays =
        holidayService.getNonWeekendPublicHolidays(year, countryCodes);

    return ResponseEntity.ok(
        new CountriesPublicHolidaysResponse(
            holidayMapper.mapToCountryHolidayDetails(nonWeekendPublicHolidays)));
  }

  @Override
  public ResponseEntity<List<LocalHoliday>> getLocalHolidays(
      final @PathVariable("year") int year,
      final @RequestParam(value = "countryCodes") @NotEmpty List<String> countryCodes) {

    countryCodes.forEach(this::validateCountryCode);
    validateMaxNoOfCountryCodes(countryCodes);

    final var deDuplicatedHolidays = holidayService.getDeDuplicatedHolidays(year, countryCodes);

    return ResponseEntity.ok(holidayMapper.mapToLocalHolidays(deDuplicatedHolidays));
  }

  private void validateCountryCode(final String countryCode) {
    if (!countryService.isValidCountryCode(countryCode)) {
      log.error("invalid country code: {}", countryCode);
      throw new BadRequestException("invalid country code : " + countryCode);
    }
  }

  private void validateMaxNoOfCountryCodes(final List<String> countryCodes) {
    if (countryCodes.size() != 2) {
      log.error("expected number of country codes is 2, got : {}", countryCodes);
      throw new BadRequestException("expected number of country codes is 2");
    }
  }
}
