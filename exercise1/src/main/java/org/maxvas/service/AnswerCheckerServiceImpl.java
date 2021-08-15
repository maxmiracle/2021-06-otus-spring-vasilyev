package org.maxvas.service;

import org.maxvas.domain.Question;
import org.springframework.stereotype.Service;

@Service
public class AnswerCheckerServiceImpl implements AnswerCheckerService {

    @Override
    public boolean checkAnswer(Question question, String answer) {
        String rightAnswer = question.getRightAnswer();
        return rightAnswer.equalsIgnoreCase(answer);
    }
}
