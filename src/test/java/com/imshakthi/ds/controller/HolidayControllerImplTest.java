package com.imshakthi.ds.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imshakthi.ds.mapper.HolidayMapper;
import com.imshakthi.ds.model.CountryAndHolidays;
import com.imshakthi.ds.model.Holiday;
import com.imshakthi.ds.model.LocalHoliday;
import com.imshakthi.ds.service.CountryService;
import com.imshakthi.ds.service.HolidayServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HolidayController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class HolidayControllerImplTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CountryService countryService;
  @MockBean private HolidayServiceImpl holidayService;
  @MockBean private HolidayMapper holidayMapper;

  @InjectMocks HolidayControllerImpl holidayController;

  @Test
  void givenInvalidCountryCode_whenGetLastCelebratedHolidays_thenReturnBadRequest()
      throws Exception {

    mockMvc
        .perform(get("/api/v1/holidays/last-celebrated/NL"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "{\"error\":\"Bad Request\",\"message\":\"invalid country code : NL\",\"status\":400}"));
  }

  @Test
  void givenValidCountryCode_whenGetLastCelebratedHolidays_thenReturnValidResponse()
      throws Exception {
    final var holidays = List.of(Holiday.builder().date("2025-01-01").name("name").build());
    final var expectedHolidays =
        List.of(new com.imshakthi.ds.dto.response.Holiday("2025-01-01", "name", null));

    when(countryService.isValidCountryCode("NL")).thenReturn(true);
    when(holidayService.getLastCelebratedHolidays("NL")).thenReturn(holidays);
    when(holidayMapper.map(holidays)).thenReturn(expectedHolidays);

    mockMvc
        .perform(get("/api/v1/holidays/last-celebrated/NL"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json("{\"holidays\":[{\"date\":\"2025-01-01\",\"name\":\"name\"}]}"));
  }

  @Test
  void givenInvalidCountryCode_whenGetPublicHolidays_thenReturnBadRequest() throws Exception {

    mockMvc
        .perform(get("/api/v1/holidays/2025/public?countryCodes=Invalid&countryCodes=Invalid2"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "{\"error\":\"Bad Request\",\"message\":\"invalid country code : Invalid\",\"status\":400}"));
  }

  @Test
  void givenValidCountryCode_whenGetPublicHolidays_thenReturnValidResponse() throws Exception {
    final var holidays = List.of(Holiday.builder().date("2025-01-01").name("name").build());
    final var countryAndHolidays =
        List.of(new CountryAndHolidays("NL", List.of()), new CountryAndHolidays("DE", holidays));
    final var expectedHolidays =
        List.of(
            new com.imshakthi.ds.dto.response.CountryHolidayDetails(
                "NL", List.of(new com.imshakthi.ds.dto.response.Holiday("2025-01-01", "h1", null))),
            new com.imshakthi.ds.dto.response.CountryHolidayDetails(
                "DE",
                List.of(new com.imshakthi.ds.dto.response.Holiday("2025-01-01", "h2", null))));

    when(countryService.isValidCountryCode("NL")).thenReturn(true);
    when(countryService.isValidCountryCode("DE")).thenReturn(true);
    when(holidayService.getNonWeekendPublicHolidays(2025, List.of("NL", "DE")))
        .thenReturn(countryAndHolidays);
    when(holidayMapper.mapToCountryHolidayDetails(countryAndHolidays)).thenReturn(expectedHolidays);

    mockMvc
        .perform(get("/api/v1/holidays/2025/public?countryCodes=NL&countryCodes=DE"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            content()
                .json(
                    "{\"holidays\":[{\"countryCode\":\"NL\",\"holidays\":[{\"date\":\"2025-01-01\",\"name\":\"h1\"}]},{\"countryCode\":\"DE\",\"holidays\":[{\"date\":\"2025-01-01\",\"name\":\"h2\"}]}]}"));
  }

  @Test
  void givenInvalidCountryCode_whenGetLocalHolidays_thenReturnBadRequest() throws Exception {

    mockMvc
        .perform(get("/api/v1/holidays/2025/local?countryCodes=Invalid1&countryCodes=Invalid2"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "{\"error\":\"Bad Request\",\"message\":\"invalid country code : Invalid1\",\"status\":400}"));
  }

  @Test
  void givenLessThanTwoCountryCode_whenGetLocalHolidays_thenReturnBadRequest() throws Exception {
    when(countryService.isValidCountryCode("NL")).thenReturn(true);

    mockMvc
        .perform(get("/api/v1/holidays/2025/local?countryCodes=NL"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "{\"error\":\"Bad Request\",\"message\":\"expected number of country codes is 2\",\"status\":400}"));
  }

  @Test
  void givenValidCountryCodes_whenGetLocalHolidays_thenReturnValidResponse() throws Exception {
    final var localHolidays =
        List.of(
            new LocalHoliday("2025-01-01", List.of("Nieuwjaarsdag", "Neujahr")),
            new LocalHoliday("2025-04-18", List.of("Goede Vrijdag", "Karfreitag")));
    final var expectedLocalHolidays =
        List.of(
            new com.imshakthi.ds.dto.response.LocalHoliday(
                "2025-01-01", List.of("Nieuwjaarsdag", "Neujahr")),
            new com.imshakthi.ds.dto.response.LocalHoliday(
                "2025-04-18", List.of("Goede Vrijdag", "Karfreitag")));

    when(countryService.isValidCountryCode("NL")).thenReturn(true);
    when(countryService.isValidCountryCode("DE")).thenReturn(true);
    when(holidayService.getDeDuplicatedHolidays(2025, List.of("NL", "DE")))
        .thenReturn(localHolidays);
    when(holidayMapper.mapToLocalHolidays(localHolidays)).thenReturn(expectedLocalHolidays);

    mockMvc
        .perform(get("/api/v1/holidays/2025/local?countryCodes=NL&countryCodes=DE"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(
            content()
                .json(
                    "[{\"date\":\"2025-01-01\",\"localNames\":[\"Nieuwjaarsdag\",\"Neujahr\"]},{\"date\":\"2025-04-18\",\"localNames\":[\"Goede Vrijdag\",\"Karfreitag\"]}]"));
  }
}
