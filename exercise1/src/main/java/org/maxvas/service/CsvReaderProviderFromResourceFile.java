package org.maxvas.service;

import org.maxvas.conf.QuizConfiguration;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;


@Component
public class CsvReaderProviderFromResourceFile implements CsvReaderProvider {

    private final String resourcePath;

    public CsvReaderProviderFromResourceFile(QuizConfiguration quizConfiguration) {
        this.resourcePath = quizConfiguration.getCsvPath();
    }

    @Override
    public Reader getReader() {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(resourcePath))));
    }
}
