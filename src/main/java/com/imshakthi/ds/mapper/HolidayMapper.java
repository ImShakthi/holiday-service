package com.imshakthi.ds.mapper;

import com.imshakthi.ds.client.model.PublicHolidayV3Dto;
import com.imshakthi.ds.dto.response.Holiday;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HolidayMapper {
  List<Holiday> map(final List<com.imshakthi.ds.model.Holiday> holidays);

  @Mapping(target = "date", source = "date")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "localName", ignore = true)
  Holiday map(final com.imshakthi.ds.model.Holiday holiday);

  List<com.imshakthi.ds.model.Holiday> mapResponse(final List<PublicHolidayV3Dto> holidays);

  @Mapping(target = "date", source = "date")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "localName", ignore = true)
  com.imshakthi.ds.model.Holiday map(final PublicHolidayV3Dto holiday);
}
