package org.maxvas.service;

import lombok.extern.slf4j.Slf4j;
import org.maxvas.QuizContext;
import org.maxvas.dao.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
public class QuizService {

    private final QuestionReader questionReader;
    private final PrintQuestion printQuestionService;
    private final Scanner scanner = new Scanner(System.in);
    private final int threshold;

    QuizService(@Value("${questions.threshold}") int threshold, QuestionReader questionReader, PrintQuestion printQuestionService) {
        this.threshold = threshold;
        this.questionReader = questionReader;
        this.printQuestionService = printQuestionService;
    }

    public void conductQuiz() {
        QuizContext quizContext = new QuizContext(threshold, scanner);
        printQuestionService.printQuestion("What is your name and surname?");
        quizContext.scanUserName();
        List<Question> questionList = questionReader.readQuestions();
        questionList.forEach(question -> {
            printQuestionService.printQuestion(question);
            quizContext.scanAnswer(question, scanner);
        });
        quizContext.showResult();
    }

}
