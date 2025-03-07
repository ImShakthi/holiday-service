package com.imshakthi.ds.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Error response model")
public class ErrorResponse {

  @Schema(description = "HTTP status code", example = "400/500")
  private int status;

  @Schema(description = "Error message", example = "Invalid request parameters/Internal server")
  private String message;
}
