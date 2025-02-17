package com.imshakthi.ds.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.imshakthi.ds.model.Country;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountryServiceTest {

  private final NagerService nagerService = mock(NagerService.class);

  private CountryService countryService;

  @BeforeEach
  void setUp() {
    when(nagerService.getAllCountries())
        .thenReturn(List.of(new Country("Netherlands", "NL"), new Country("Germany", "DE")));

    countryService = new CountryService(nagerService);
  }

  @Test
  void isValidCountryCode() {
    // Valid country code
    assertTrue(countryService.isValidCountryCode("NL"), "NL should be valid");
    assertTrue(countryService.isValidCountryCode("nl"), "nl should be valid");
    assertTrue(countryService.isValidCountryCode("De"), "De should be valid");

    // Invalid country code
    assertFalse(countryService.isValidCountryCode("us"), "US should not be invalid");
    assertFalse(countryService.isValidCountryCode("US"), "US should not be invalid");
  }
}
