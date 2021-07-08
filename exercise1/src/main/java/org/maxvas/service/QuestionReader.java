package org.maxvas.service;

import org.maxvas.dao.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionReader {
    List<Question> readQuestions() throws IOException;
}
