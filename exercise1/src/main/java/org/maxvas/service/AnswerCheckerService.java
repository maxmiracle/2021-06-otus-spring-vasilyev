package org.maxvas.service;

import org.maxvas.domain.Question;

public interface AnswerCheckerService {
    boolean checkAnswer(Question question, String answer);
}
