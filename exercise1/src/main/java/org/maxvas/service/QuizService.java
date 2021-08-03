package org.maxvas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maxvas.dao.Question;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
@Component
public class QuizService implements ApplicationContextAware {

    private final QuestionReader questionReader;
    private final PrintQuestion printQuestionService;
    private ApplicationContext applicationContext;

    public void conductQuiz() throws IOException {
        String userName;
        System.out.println("What is your name and surname?");
        Scanner scanner = new Scanner(System.in);
        userName = scanner.next();
        MarkService markService = applicationContext.getBean(MarkService.class);
        List<Question> questionList = questionReader.readQuestions();
        questionList.forEach(question -> {
            printQuestionService.printQuestion(question);
            markService.getAnswer(question, scanner);
        });
        if (markService.isTestPassed()) {
            System.out.println("Test passed!");
        } else {
            System.out.println("Test failed. Try next time.");
        }
        scanner.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
