package com.imshakthi.ds.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.imshakthi.ds.model.CountryAndHolidays;
import com.imshakthi.ds.model.Holiday;
import com.imshakthi.ds.model.HolidayTypes;
import com.imshakthi.ds.model.LocalHoliday;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HolidayServiceImplTest {

  @Mock private NagerService nagerService;

  private HolidayService holidayService;

  @BeforeEach
  void setUp() {
    holidayService = new HolidayServiceImpl(nagerService);
  }

  @Test
  void givenCountryCode_whenCurrentYearHasMoreThan3CelebratedHolidays_thenReturnValidHolidays() {
    final var expected =
        List.of(
            new Holiday("2025-01-03", "celebrated holiday", null, List.of()),
            new Holiday("2025-01-02", "celebrated holiday", null, List.of()),
            new Holiday("2025-01-01", "celebrated holiday", null, List.of()));

    final var holidaysResponse =
        List.of(
            new Holiday("2025-01-01", "celebrated holiday", null, List.of()),
            new Holiday("2025-01-02", "celebrated holiday", null, List.of()),
            new Holiday("2025-01-03", "celebrated holiday", null, List.of()),
            new Holiday("2025-12-01", "not yet celebrated holiday", null, List.of()),
            new Holiday("2025-12-02", "not yet celebrated holiday", null, List.of()),
            new Holiday("2025-12-03", "not yet celebrated holiday", null, List.of()));
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
            new Holiday("2025-01-01", "celebrated holiday", null, List.of()),
            new Holiday("2024-12-26", "holiday", null, List.of()),
            new Holiday("2024-12-25", "holiday", null, List.of()));

    final var thisYearResponse =
        List.of(
            new Holiday("2025-01-01", "celebrated holiday", null, List.of()),
            new Holiday("2025-12-03", "not yet celebrated holiday", null, List.of()));
    final var thisPrevResponse =
        List.of(
            new Holiday("2024-12-26", "holiday", null, List.of()),
            new Holiday("2024-12-25", "holiday", null, List.of()),
            new Holiday("2024-11-11", "holiday", null, List.of()),
            new Holiday("2024-10-18", "holiday", null, List.of()));

    when(nagerService.getHolidays(2025, "NL")).thenReturn(thisYearResponse);
    when(nagerService.getHolidays(2024, "NL")).thenReturn(thisPrevResponse);

    final var actual = holidayService.getLastCelebratedHolidays("NL");

    verify(nagerService, times(1)).getHolidays(2025, "NL");
    verify(nagerService, times(1)).getHolidays(2024, "NL");

    assertNotNull(actual);
    assertEquals(3, actual.size());
    assertEquals(expected, actual, "3 celebrated holidays from this year and previous year");
  }

  @Test
  void givenYearAndCountryCode_whenNonWeekendPublicHolidaysIsCalled_thenReturnValidHolidays() {
    final var publicHolidayTypes = List.of(HolidayTypes.PUBLIC);
    // public holidays
    final var h1 = Holiday.builder().date("2025-02-03").holidayTypes(publicHolidayTypes).build();
    final var h2 = Holiday.builder().date("2025-01-03").holidayTypes(publicHolidayTypes).build();
    final var h3 = Holiday.builder().date("2025-03-03").holidayTypes(publicHolidayTypes).build();

    final var holidays =
        List.of(
            h1,
            h2,
            h3,
            Holiday.builder()
                .date("2025-01-04") // weekend and non public holiday
                .build(),
            Holiday.builder()
                .date("2025-01-18") // weekend - SATURDAY
                .holidayTypes(publicHolidayTypes) // public holiday
                .build(),
            Holiday.builder()
                .date("2025-01-19") // weekend - SUNDAY
                .holidayTypes(publicHolidayTypes) // public holiday
                .build());

    // sorted in descending order
    final var expectedPublicHolidays = List.of(h3, h1, h2);
    final var expected =
        List.of(
            CountryAndHolidays.builder().countryCode("NL").holidays(expectedPublicHolidays).build(),
            CountryAndHolidays.builder()
                .countryCode("DE")
                .holidays(expectedPublicHolidays)
                .build());

    when(nagerService.getHolidays(2025, "NL")).thenReturn(holidays);
    when(nagerService.getHolidays(2025, "DE")).thenReturn(holidays);

    final var actual = holidayService.getNonWeekendPublicHolidays(2025, List.of("NL", "DE"));

    verify(nagerService, times(1)).getHolidays(2025, "NL");
    verify(nagerService, times(1)).getHolidays(2025, "DE");

    assertNotNull(actual);
    assertEquals(2, actual.size());
    assertEquals(expected, actual, "non weekend public holidays from this year for NL and DE");
  }

  @Test
  void
      givenYearAndCountry_whenDeDuplicatedHolidaysIsCalled_thenReturnValidHolidaysWithLocalNames() {
    final var expected =
        List.of(
            LocalHoliday.builder()
                .date("2025-01-01")
                .localNames(List.of("Nieuwjaarsdag", "Neujahr"))
                .build(),
            LocalHoliday.builder()
                .date("2025-04-20")
                .localNames(List.of("Eerste Paasdag", "Ostersonntag"))
                .build());

    final var nlHolidays =
        List.of(
            Holiday.builder().date("2025-01-01").localName("Nieuwjaarsdag").build(),
            Holiday.builder().date("2025-04-20").localName("Eerste Paasdag").build(),
            Holiday.builder().date("2025-05-20").localName("non common holiday").build());
    final var deHolidays =
        List.of(
            Holiday.builder().date("2025-01-01").localName("Neujahr").build(),
            Holiday.builder().date("2025-04-20").localName("Ostersonntag").build(),
            Holiday.builder().date("2025-10-20").localName("non common holiday").build());

    when(nagerService.getHolidays(2025, "NL")).thenReturn(nlHolidays);
    when(nagerService.getHolidays(2025, "DE")).thenReturn(deHolidays);

    final var actual = holidayService.getDeDuplicatedHolidays(2025, List.of("NL", "DE"));

    verify(nagerService, times(1)).getHolidays(2025, "NL");
    verify(nagerService, times(1)).getHolidays(2025, "DE");

    assertNotNull(actual);
    assertEquals(2, actual.size());
    assertEquals(expected, actual, "common holidays from this year for NL and DE with local name");
  }
}
