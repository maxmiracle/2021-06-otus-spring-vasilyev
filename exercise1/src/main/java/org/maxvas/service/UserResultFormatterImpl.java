package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.maxvas.conf.QuizConfiguration;
import org.maxvas.domain.QuizResult;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class UserResultFormatterImpl implements UserResultFormatter {

    private final static String RESULT_INFO = "result.info";
    private final static String TEST_PASSED = "test.passed";
    private final static String TEST_FAILED = "test.failed";
    private final MessageSource messageSource;
    private final QuizConfiguration quizConfiguration;

    public String formatUserResult(QuizResult quizResult) {
        Locale locale = Locale.forLanguageTag(quizConfiguration.getLocale());
        return messageSource.getMessage(RESULT_INFO, new Object[]{
                        quizResult.getUser().getFirstName(),
                        quizResult.getUser().getLastName(),
                        quizResult.getRightAnswers(),
                        messageSource.getMessage(quizResult.isTestPassed() ? TEST_PASSED : TEST_FAILED, null, locale)},
                locale);
    }

}
