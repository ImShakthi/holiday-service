package com.imshakthi.ds.model;

import java.util.List;
import lombok.Builder;

@Builder
public record LocalHoliday(String date, List<String> localNames) {}
