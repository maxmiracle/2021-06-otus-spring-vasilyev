package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.maxvas.conf.LocaleConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class LocaleMessageService {

    private final LocaleConfiguration localeConfiguration;
    private final MessageSource messageSource;

    public String getMessage(String s, Object... params) {
        String localeTag = localeConfiguration.getLocale();
        Locale locale = Locale.forLanguageTag(localeTag);
        return messageSource.getMessage(s, params, locale);
    }
}
