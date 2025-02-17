package com.imshakthi.ds.service;

import static com.imshakthi.ds.util.DateUtil.getCurrentYear;
import static com.imshakthi.ds.util.DateUtil.parseDate;
import static com.imshakthi.ds.util.DateUtil.today;

import com.imshakthi.ds.model.CountryAndHolidays;
import com.imshakthi.ds.model.Holiday;
import com.imshakthi.ds.model.LocalHoliday;
import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class HolidayServiceImpl implements HolidayService {
  private static final int MAX_LAST_HOLIDAYS = 3;

  private final NagerService nagerService;

  @Override
  public List<Holiday> getLastCelebratedHolidays(final String countryCode) {
    final int currentYear = getCurrentYear();
    final var last3Holidays = getLastCelebratedHolidays(currentYear, countryCode);

    if (last3Holidays.isEmpty() || last3Holidays.size() < MAX_LAST_HOLIDAYS) {
      return computeCelebratedHolidaysFor(currentYear - 1, countryCode, last3Holidays);
    }

    log.info("last 3 holidays fetched.");
    return last3Holidays;
  }

  @Override
  public List<CountryAndHolidays> getNonWeekendPublicHolidays(
      final int year, final List<String> countryCodes) {

    return countryCodes.parallelStream()
        .map(countryCode -> getNonWeekendPublicHolidays(year, countryCode))
        .toList();
  }

  @Override
  public List<LocalHoliday> getDeDuplicatedHolidays(
      final int year, final List<String> countryCodes) {
    // Country #1
    final var hoildayNameMap =
        nagerService.getHolidays(year, countryCodes.getFirst()).stream()
            .collect(Collectors.toMap(Holiday::date, Function.identity()));

    // Country #2
    return nagerService.getHolidays(year, countryCodes.getLast()).stream()
        .filter(holiday -> hoildayNameMap.containsKey(holiday.date()))
        .map(
            h2 -> {
              var h1 = hoildayNameMap.get(h2.date());
              var holidays = List.of(h1.localName(), h2.localName());
              return new LocalHoliday(h2.date(), holidays);
            })
        .toList();
  }

  private List<Holiday> computeCelebratedHolidaysFor(
      final int year, final String countryCode, final List<Holiday> last3Holidays) {
    final int missingNoHolidays = MAX_LAST_HOLIDAYS - last3Holidays.size();
    log.info(
        "current year {} has only #{} holidays need to fetch missing #{} holidays from prev year.",
        year,
        last3Holidays.size(),
        missingNoHolidays);

    final var last3PrevYearHolidays =
        getLastCelebratedHolidays(year, countryCode).stream().limit(missingNoHolidays).toList();

    return Stream.concat(last3Holidays.stream(), last3PrevYearHolidays.stream()).toList();
  }

  private CountryAndHolidays getNonWeekendPublicHolidays(final int year, final String countryCode) {

    final var holidays =
        nagerService.getHolidays(year, countryCode).stream()
            .filter(holiday -> isNotWeekend(parseDate(holiday.date()).getDayOfWeek()))
            .filter(Holiday::isPublicHoliday)
            .sorted(Comparator.comparing(Holiday::date).reversed())
            .toList();

    return new CountryAndHolidays(countryCode, holidays);
  }

  private boolean isNotWeekend(final DayOfWeek dayOfWeek) {
    return !(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
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
