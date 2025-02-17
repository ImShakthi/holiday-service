package com.imshakthi.ds.mapper;

import com.imshakthi.ds.client.model.CountryV3Dto;
import com.imshakthi.ds.model.Country;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
  List<Country> map(final List<CountryV3Dto> dtos);

  @Mapping(target = "countryCode", source = "countryCode")
  @Mapping(target = "name", source = "name")
  Country map(final CountryV3Dto countryV3Dto);
}
