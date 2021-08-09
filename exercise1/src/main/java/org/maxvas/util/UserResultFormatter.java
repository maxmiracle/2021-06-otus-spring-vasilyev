package org.maxvas.util;

import org.maxvas.domain.QuizResult;

public class UserResultFormatter {
    public static String formatUserResult(QuizResult quizResult) {
        return String.format("\n  First name: %s\n  Last name: %s\n  Right answers: %s\n  Test result: %s\n",
                quizResult.getUser().getFirstName(),
                quizResult.getUser().getLastName(),
                quizResult.getRightAnswers(),
                quizResult.isTestPassed() ? "Passed" : "Failed");
    }

}
