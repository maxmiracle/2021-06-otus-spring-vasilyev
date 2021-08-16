package org.maxvas.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "questions")
@Getter
@Setter
@Component
public class QuizConfiguration {

    private final static String CSV_PATH = "csv.path";

    private final MessageSource messageSource;
    private int threshold;
    private String locale;

    QuizConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getCsvPath() {
        return messageSource.getMessage(CSV_PATH, null, Locale.forLanguageTag(getLocale()));
    }
}
