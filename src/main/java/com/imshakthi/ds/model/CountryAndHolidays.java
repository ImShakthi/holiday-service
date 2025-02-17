package com.imshakthi.ds.model;

import java.util.List;
import lombok.Builder;

@Builder
public record CountryAndHolidays(String countryCode, List<Holiday> holidays) {}
