package com.example.animals.csv.converter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<List<Map<String, String>>> {

    public CsvHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return List.class == clazz;
    }

    @Override
    protected List<Map<String, String>> readInternal(Class<? extends List<Map<String, String>>> clazz, HttpInputMessage inputMessage) throws IOException {
        try (CSVParser csvParser = new CSVParser(new InputStreamReader(inputMessage.getBody()), CSVFormat.DEFAULT.withHeader())) {
            return csvParser.getRecords()
                    .stream()
                    .map(CSVRecord::toMap)
                    .collect(Collectors.toList());
        }
    }

    @Override
    protected void writeInternal(List<Map<String, String>> list, HttpOutputMessage outputMessage) throws IOException {
    }
}
