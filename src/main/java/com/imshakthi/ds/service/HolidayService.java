package com.imshakthi.ds.service;

import static com.imshakthi.ds.util.DateUtil.getCurrentYear;
import static com.imshakthi.ds.util.DateUtil.parseDate;
import static com.imshakthi.ds.util.DateUtil.today;

import com.imshakthi.ds.model.Holiday;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class HolidayService {
  private static final int MAX_LAST_HOLIDAYS = 3;

  private final NagerService nagerService;

  public List<Holiday> getLastCelebratedHolidays(final String countryCode) {

    final int currentYear = getCurrentYear();
    final var last3Holidays = getLastCelebratedHolidays(currentYear, countryCode);

    if (last3Holidays.isEmpty() || last3Holidays.size() < MAX_LAST_HOLIDAYS) {
      final int missingNoHolidays = MAX_LAST_HOLIDAYS - last3Holidays.size();

      log.info(
          "current year {} has only #{} holidays need to fetch missing #{} holidays from prev year.",
          currentYear,
          last3Holidays.size(),
          missingNoHolidays);

      final var last3PrevYearHolidays =
          getLastCelebratedHolidays(currentYear - 1, countryCode).stream()
              .limit(missingNoHolidays)
              .toList();

      return Stream.concat(last3Holidays.stream(), last3PrevYearHolidays.stream()).toList();
    }
    log.info("last 3 holidays fetched.");

    return last3Holidays;
  }

  private List<Holiday> getLastCelebratedHolidays(final int year, final String countryCode) {
    final var holidays = nagerService.getHolidays(year, countryCode);

    final var today = today();

    return holidays.stream()
        .filter(holiday -> parseDate(holiday.date()).isBefore(today))
        .sorted(Comparator.comparing(Holiday::date).reversed())
        .limit(MAX_LAST_HOLIDAYS)
        .toList();
  }
}
