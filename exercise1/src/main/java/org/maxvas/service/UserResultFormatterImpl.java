package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.maxvas.domain.QuizResult;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserResultFormatterImpl implements UserResultFormatter {

    private final static String RESULT_INFO = "result.info";
    private final static String TEST_PASSED = "test.passed";
    private final static String TEST_FAILED = "test.failed";
    private final LocaleMessageService localeMessageService;

    public String formatUserResult(QuizResult quizResult) {
        return localeMessageService.getMessage(RESULT_INFO,
                quizResult.getUser().getFirstName(),
                quizResult.getUser().getLastName(),
                quizResult.getRightAnswers(),
                localeMessageService.getMessage(quizResult.isTestPassed() ? TEST_PASSED : TEST_FAILED));
    }

}
