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
@Schema(description = "public holidays of a country")
public class CountryWithPublicHolidays {
  @Schema(description = "name of country")
  private String country;

  @Schema(description = "list of public holidays")
  private List<Holiday> holidays;
}
