package com.imshakthi.ds.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
  public static String formatDate(final LocalDate date) {
    return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  public static int getCurrentYear() {
    return LocalDate.now().getYear();
  }

  public static LocalDate today() {
    return LocalDate.now();
  }

  public static LocalDate parseDate(final String date) {
    return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
  }
}
