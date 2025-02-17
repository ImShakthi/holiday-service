package com.imshakthi.ds.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imshakthi.ds.model.Holiday;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

  @Mock private NagerService nagerService;

  private HolidayService holidayService;

  @BeforeEach
  void setUp() {
    holidayService = new HolidayService(nagerService);
  }

  @Test
  void givenCountryCode_whenCurrentYearHasMoreThan3CelebratedHolidays_thenReturnValidHolidays() {
    final var expected =
        List.of(
            new Holiday("2025-01-03", "celebrated holiday", null),
            new Holiday("2025-01-02", "celebrated holiday", null),
            new Holiday("2025-01-01", "celebrated holiday", null));

    final var holidaysResponse =
        List.of(
            new Holiday("2025-01-01", "celebrated holiday", null),
            new Holiday("2025-01-02", "celebrated holiday", null),
            new Holiday("2025-01-03", "celebrated holiday", null),
            new Holiday("2025-12-01", "not yet celebrated holiday", null),
            new Holiday("2025-12-02", "not yet celebrated holiday", null),
            new Holiday("2025-12-03", "not yet celebrated holiday", null));
    when(nagerService.getHolidays(2025, "NL")).thenReturn(holidaysResponse);

    final var actual = holidayService.getLastCelebratedHolidays("NL");

    verify(nagerService, times(1)).getHolidays(2025, "NL");

    assertNotNull(actual);
    assertEquals(3, actual.size());
    assertEquals(expected, actual, "3 celebrated holidays from this year");
  }

  @Test
  void
      givenCountryCode_whenCurrentYearHasLessThan3CelebratedHolidays_thenReturnValidHolidaysFromPreviousYear() {
    final var expected =
        List.of(
            new Holiday("2025-01-01", "celebrated holiday", null),
            new Holiday("2024-12-26", "holiday", null),
            new Holiday("2024-12-25", "holiday", null));

    final var thisYearResponse =
        List.of(
            new Holiday("2025-01-01", "celebrated holiday", null),
            new Holiday("2025-12-03", "not yet celebrated holiday", null));
    final var thisPrevResponse =
        List.of(
            new Holiday("2024-12-26", "holiday", null),
            new Holiday("2024-12-25", "holiday", null),
            new Holiday("2024-11-11", "holiday", null),
            new Holiday("2024-10-18", "holiday", null));

    when(nagerService.getHolidays(2025, "NL")).thenReturn(thisYearResponse);
    when(nagerService.getHolidays(2024, "NL")).thenReturn(thisPrevResponse);

    final var actual = holidayService.getLastCelebratedHolidays("NL");

    verify(nagerService, times(1)).getHolidays(2025, "NL");
    verify(nagerService, times(1)).getHolidays(2024, "NL");

    assertNotNull(actual);
    assertEquals(3, actual.size());
    assertEquals(expected, actual, "3 celebrated holidays from this year and previous year");
  }
}
