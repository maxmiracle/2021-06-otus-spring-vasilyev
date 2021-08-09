package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.maxvas.domain.Question;
import org.maxvas.domain.QuizResult;
import org.maxvas.domain.User;
import org.maxvas.util.UserResultFormatter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuizService {

    private static final String FIRST_NAME_QUESTION = "What is your first name?";
    private static final String LAST_NAME_QUESTION = "What is your last name?";
    private final QuestionReader questionReader;
    private final PrintQuestionService printQuestionService;
    private final IOService ioService;
    private final QuizResultsAssessor quizResultsAssessor;
    private final AnswerCheckerService answerCheckerService;

    public void conductQuiz() {
        QuizResult quizResult = new QuizResult();
        fillInitialInfo(quizResult);
        List<Question> questionList = questionReader.readQuestions();
        askQuestions(quizResult, questionList);
        quizResultsAssessor.assesAndSetResults(quizResult);
        ioService.print(UserResultFormatter.formatUserResult(quizResult));
    }

    private void askQuestions(QuizResult quizResult, List<Question> questionList) {
        questionList.forEach(question -> {
            printQuestionService.printQuestion(question);
            String answer = ioService.getAnswer();
            if (answerCheckerService.checkAnswer(question, answer)) {
                quizResult.incrementRightAnswersCount();
            }
        });
    }

    private void fillInitialInfo(QuizResult quizResult) {
        printQuestionService.printQuestion(FIRST_NAME_QUESTION);
        User user = new User();
        user.setFirstName(ioService.getAnswer());
        printQuestionService.printQuestion(LAST_NAME_QUESTION);
        user.setLastName(ioService.getAnswer());
        quizResult.setUser(user);
    }

}
