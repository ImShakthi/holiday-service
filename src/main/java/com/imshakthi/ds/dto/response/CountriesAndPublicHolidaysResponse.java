package com.imshakthi.ds.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "public holidays of given countries response")
public class CountriesAndPublicHolidaysResponse {
    private List<CountryWithPublicHolidays> holidays;
}
