package org.maxvas;

import org.maxvas.dao.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizContext {
    private final List<Object> answers = new ArrayList<>();
    private final int threshold;
    private final Scanner scanner;
    private int rightAnswersCount = 0;
    private String userName;

    public QuizContext(int threshold, Scanner scanner) {
        this.threshold = threshold;
        this.scanner = scanner;
    }

    public void scanUserName() {
        userName = scanner.next();
    }


    public void scanAnswer(Question question, Scanner scanner) {
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
        if (result)
            rightAnswersCount++;
    }

    public void showResult() {
        if (isTestPassed()) {
            System.out.printf("User %s - passed!\n", userName);
        } else {
            System.out.printf("User %s failed. Try next time.\n", userName);
        }
    }

    public boolean isTestPassed() {
        return rightAnswersCount >= threshold;
    }
}
