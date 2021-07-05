package org.maxvas.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

@AllArgsConstructor
public class CsvDataProvider {

    @NonNull
    private final String resourcePath;

    public Reader getReader(){
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(this.getClass().getResourceAsStream(resourcePath))));
    }
}
