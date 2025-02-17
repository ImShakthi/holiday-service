package com.imshakthi.ds.service;


import com.imshakthi.ds.api.NagerApi;
import com.imshakthi.ds.mapper.CountryMapper;
import com.imshakthi.ds.mapper.HolidayMapper;
import com.imshakthi.ds.model.Country;
import com.imshakthi.ds.model.Holiday;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NagerService {

  private final NagerApi nagerApi;
  private final CountryMapper countryMapper;
  private final HolidayMapper holidayMapper;

  public List<Country> getAllCountries() {
    final var countries = nagerApi.getAllCountries();

    return countryMapper.map(countries);
  }

  public List<Holiday> getHolidays(final int year, final String countryCode) {
    final var holidaysResponse = nagerApi.getPublicHolidays(year, countryCode);

    return holidayMapper.mapResponse(holidaysResponse);
  }
}
