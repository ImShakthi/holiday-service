package com.imshakthi.ds.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Holiday details.")
public class Holiday {
  @Schema(description = "Date of holiday.")
  private ZonedDateTime date;

  @Schema(description = "Name of holiday.")
  private String name;

  @Schema(description = "Local name of the holiday.")
  private String localName;
}
