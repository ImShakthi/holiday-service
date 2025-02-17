package com.imshakthi.ds.service;

import com.imshakthi.ds.model.CountryAndHolidays;
import com.imshakthi.ds.model.Holiday;
import com.imshakthi.ds.model.LocalHoliday;
import java.util.List;

public interface HolidayService {
  /**
   * Method to get last celebrated holidays.
   *
   * @param countryCode code of the country.
   * @return list of holidays.
   */
  List<Holiday> getLastCelebratedHolidays(final String countryCode);

  /**
   * Method to get list of non weekend public holidays in given country
   *
   * @param year year in integer
   * @param countryCodes codes of the country
   * @return list of countries and holiday
   */
  List<CountryAndHolidays> getNonWeekendPublicHolidays(
      final int year, final List<String> countryCodes);

  /**
   * Method to get deduplicated holidays.
   *
   * @param year year in integer
   * @param countryCodes codes of the country
   * @return list of local holidays
   */
  List<LocalHoliday> getDeDuplicatedHolidays(final int year, final List<String> countryCodes);
}
