package com.imshakthi.ds.service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CountryService {
  private final Set<String> countryCodes = new ConcurrentSkipListSet<>();

  public CountryService(final NagerService nagerService) {
    final var countries = nagerService.getAllCountries();

    countries.forEach(
        country -> {
          countryCodes.add(country.countryCode().toUpperCase());
        });

    log.info("Country codes: {}", countryCodes);
  }

  public boolean isValidCountryCode(final String countryCode) {
    return countryCodes.contains(countryCode.toUpperCase());
  }
}
