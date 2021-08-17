package org.maxvas.service;

import org.maxvas.conf.LocaleConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleMessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(LocaleConfiguration localeConfiguration,
                                MessageSource messageSource) {
        this.messageSource = messageSource;
        String localeTag = localeConfiguration.getLocale();
        this.locale = Locale.forLanguageTag(localeTag);
    }

    public String getMessage(String s, Object... params) {
        return messageSource.getMessage(s, params, locale);
    }
}
