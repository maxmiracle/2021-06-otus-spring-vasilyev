package org.maxvas.service;

import org.maxvas.domain.Question;

import java.util.List;

public interface QuestionReader {
    List<Question> readQuestions();
}
