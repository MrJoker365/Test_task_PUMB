package com.example.animals.config;

import com.example.animals.csv.converter.CsvHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.Map;
import java.util.List;

@Configuration
public class CsvConverterConfig {

    @Bean
    public HttpMessageConverter<List<Map<String, String>>> csvHttpMessageConverter() {
        return new CsvHttpMessageConverter();
    }
}