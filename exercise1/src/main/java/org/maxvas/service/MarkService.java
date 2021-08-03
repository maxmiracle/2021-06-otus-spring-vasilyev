package org.maxvas.service;

import org.maxvas.dao.Question;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarkService {

    private int rightAnswersCount = 0;

    private final List<Object> answers = new ArrayList<>();

    @Value("${questions.threshold}")
    private int threshold;

    public void getAnswer(Question question, Scanner scanner) {
        String rightAnswer = question.getOptions().get(question.getRightAnswerIndex() - 1);
        scanner.nextLine();
        boolean result = false;
        switch (question.getAnswerType()) {
            case STRING:
                String answer = scanner.next();
                answers.add(answer);
                result = rightAnswer.equalsIgnoreCase(answer);
                break;
            case INTEGER:
            case OPTIONS:
                Integer answerInt = scanner.nextInt();
                answers.add(answerInt);
                result = rightAnswer.equalsIgnoreCase(answerInt.toString());
                break;
        }
        if (result == true)
            rightAnswersCount++;
    }

    public int getRightAnswersCount() {
        return rightAnswersCount;
    }

    public boolean isTestPassed() {
        return rightAnswersCount >= threshold;
    }
}
