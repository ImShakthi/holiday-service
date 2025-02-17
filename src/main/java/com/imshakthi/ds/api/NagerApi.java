package com.imshakthi.ds.api;

import com.imshakthi.ds.client.api.CountryApi;
import com.imshakthi.ds.client.api.PublicHolidayApi;
import com.imshakthi.ds.client.model.CountryV3Dto;
import com.imshakthi.ds.client.model.PublicHolidayV3Dto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NagerApi {
  private final CountryApi countryApi;

  private final PublicHolidayApi publicHolidayApi;

  public List<CountryV3Dto> getAllCountries() {
    return countryApi.countryAvailableCountries().collectList().block();
  }

  public List<PublicHolidayV3Dto> getPublicHolidays(final int year, final String countryCode) {
    return publicHolidayApi.publicHolidayPublicHolidaysV3(year, countryCode).collectList().block();
  }
}
