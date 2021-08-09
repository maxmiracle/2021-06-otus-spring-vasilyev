package org.maxvas.service;

import org.maxvas.domain.QuizResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuizResultsAssessorImpl implements QuizResultsAssessor {

    private final int threshold;

    public QuizResultsAssessorImpl(@Value("${questions.threshold}") int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void assesAndSetResults(QuizResult quizResult) {
        quizResult.setTestPassed(quizResult.getRightAnswers() >= threshold);
    }
}
