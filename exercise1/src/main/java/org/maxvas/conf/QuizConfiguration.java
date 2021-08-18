package org.maxvas.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "questions")
@Getter
@Setter
@Component
public class QuizConfiguration implements CsvFileConfiguration, AssessmentConfiguration, LocaleConfiguration {

    private String csvFilename;
    private int threshold;
    private String locale;

    @Override
    public String getCsvPath() {
        return String.format("/%s_%s.csv", csvFilename, locale);
    }
}
