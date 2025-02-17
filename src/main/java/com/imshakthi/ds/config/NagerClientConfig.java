package com.imshakthi.ds.config;

import com.imshakthi.ds.client.ApiClient;
import com.imshakthi.ds.client.api.CountryApi;
import com.imshakthi.ds.client.api.PublicHolidayApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NagerClientConfig {
  @Value("${nager.base-url}")
  private String nagerBaseUrl;

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(nagerBaseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  @Bean
  public ApiClient apiClient(final WebClient webClient) {
    ApiClient apiClient = new ApiClient(webClient);
    apiClient.setBasePath(nagerBaseUrl);
    return apiClient;
  }

  @Bean
  public CountryApi countryApi(final ApiClient apiClient) {
    return new CountryApi(apiClient);
  }

  @Bean
  public PublicHolidayApi publicHolidayApi(final ApiClient apiClient) {
    return new PublicHolidayApi(apiClient);
  }
}
