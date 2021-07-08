package org.maxvas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maxvas.dao.Question;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class QuizService {

    private final QuestionReader questionReader;

    private final PrintQuestion printQuestionService;

    public void conductQuiz() throws IOException {
        List<Question> questionList = questionReader.readQuestions();
        questionList.forEach(printQuestionService::printQuestion);
    }
}
