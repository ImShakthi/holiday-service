package com.imshakthi.ds.model;

import java.util.List;
import lombok.Builder;

@Builder
public record Holiday(String date, String name, String localName, List<HolidayTypes> holidayTypes) {
  public boolean isPublicHoliday() {
    return holidayTypes != null && holidayTypes.contains(HolidayTypes.PUBLIC);
  }
}
