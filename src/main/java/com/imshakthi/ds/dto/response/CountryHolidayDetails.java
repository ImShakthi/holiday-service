package com.imshakthi.ds.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "holidays of a country")
public class CountryHolidayDetails {
  @Schema(description = "code of country")
  private String countryCode;

  @Schema(description = "list of holidays")
  private List<Holiday> holidays;
}
