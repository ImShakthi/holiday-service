package com.imshakthi.ds.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** List of last celebrated holidays response */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "List of last celebrated holidays")
public class LastCelebratedHolidaysResponse {

  @Schema(description = "list of holidays")
  private List<Holiday> holidays;
}
