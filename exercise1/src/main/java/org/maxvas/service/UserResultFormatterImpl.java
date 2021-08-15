package org.maxvas.service;

import org.maxvas.domain.QuizResult;
import org.maxvas.service.UserResultFormatter;
import org.springframework.stereotype.Service;

@Service
public class UserResultFormatterImpl implements UserResultFormatter {
    public String formatUserResult(QuizResult quizResult) {
        return String.format("\n  First name: %s\n  Last name: %s\n  Right answers: %s\n  Test result: %s\n",
                quizResult.getUser().getFirstName(),
                quizResult.getUser().getLastName(),
                quizResult.getRightAnswers(),
                quizResult.isTestPassed() ? "Passed" : "Failed");
    }

}
