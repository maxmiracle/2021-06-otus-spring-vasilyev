package org.maxvas.service;

import org.maxvas.dao.Question;

import java.util.List;

public interface QuestionReader {
    List<Question> readQuestions();
}
