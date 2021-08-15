package org.maxvas.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuizResult {
    private int rightAnswers;
    private boolean isTestPassed;
    private User user;

    public void incrementRightAnswersCount() {
        rightAnswers++;
    }
}
