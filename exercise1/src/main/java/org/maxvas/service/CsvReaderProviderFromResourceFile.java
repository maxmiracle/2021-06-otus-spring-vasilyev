package org.maxvas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;


@Component
public class CsvReaderProviderFromResourceFile implements CsvReaderProvider {

    private final String resourcePath;

    public CsvReaderProviderFromResourceFile(@Value("${questions.csv.path}") String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public Reader getReader() {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(resourcePath))));
    }
}
