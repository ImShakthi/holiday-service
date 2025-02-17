package com.imshakthi.ds.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Local holiday details.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocalHoliday {
  @Schema(description = "Date of holiday.", example = "2025-01-01")
  private String date;

  @Schema(description = "Local name of the holidays.")
  private List<String> localNames;
}
