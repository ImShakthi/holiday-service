package com.imshakthi.ds.mapper;

import com.imshakthi.ds.client.model.PublicHolidayV3Dto;
import com.imshakthi.ds.dto.response.CountryHolidayDetails;
import com.imshakthi.ds.dto.response.Holiday;
import com.imshakthi.ds.dto.response.LocalHoliday;
import com.imshakthi.ds.model.CountryAndHolidays;
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
  @Mapping(target = "localName", source = "localName")
  @Mapping(target = "holidayTypes", source = "types")
  com.imshakthi.ds.model.Holiday map(final PublicHolidayV3Dto holiday);

  List<CountryHolidayDetails> mapToCountryHolidayDetails(final List<CountryAndHolidays> holidays);

  @Mapping(target = "countryCode", source = "countryCode")
  @Mapping(target = "holidays", source = "holidays")
  CountryHolidayDetails map(final CountryAndHolidays countryAndHolidays);

  List<LocalHoliday> mapToLocalHolidays(
      final List<com.imshakthi.ds.model.LocalHoliday> localHoliday);

  @Mapping(target = "date", source = "date")
  @Mapping(target = "localNames", source = "localNames")
  LocalHoliday map(final com.imshakthi.ds.model.LocalHoliday localHoliday);
}
