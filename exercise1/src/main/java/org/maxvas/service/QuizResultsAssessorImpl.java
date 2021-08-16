package org.maxvas.service;

import org.maxvas.conf.QuizConfiguration;
import org.maxvas.domain.QuizResult;
import org.springframework.stereotype.Service;

@Service
public class QuizResultsAssessorImpl implements QuizResultsAssessor {

    private final int threshold;

    public QuizResultsAssessorImpl(QuizConfiguration quizConfiguration) {
        this.threshold = quizConfiguration.getThreshold();
    }

    @Override
    public boolean assesResults(QuizResult quizResult) {
        return quizResult.getRightAnswers() >= threshold;
    }
}
