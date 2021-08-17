package org.maxvas.service;

import lombok.AllArgsConstructor;
import org.maxvas.conf.QuizConfiguration;
import org.maxvas.domain.Question;
import org.maxvas.domain.QuizResult;
import org.maxvas.domain.User;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class QuizService {

    private static final String FIRST_NAME_QUESTION = "question.firstname";
    private static final String LAST_NAME_QUESTION = "question.lastname";
    private final QuestionReader questionReader;
    private final PrintQuestionService printQuestionService;
    private final IOService ioService;
    private final QuizResultsAssessor quizResultsAssessor;
    private final AnswerCheckerService answerCheckerService;
    private final UserResultFormatter userResultFormatter;
    private final LocaleMessageService localeMessageService;

    public QuizResult conductQuiz() {
        User user = askUserName();
        List<Question> questionList = questionReader.readQuestions();
        QuizResult quizResult = askQuestions(user, questionList);
        quizResult.setTestPassed(quizResultsAssessor.assesResults(quizResult));
        ioService.print(userResultFormatter.formatUserResult(quizResult));
        return quizResult;
    }

    private QuizResult askQuestions(User user, List<Question> questionList) {
        QuizResult quizResult = new QuizResult();
        quizResult.setUser(user);
        questionList.forEach(question -> {
            printQuestionService.printQuestion(question);
            String answer = ioService.getAnswer();
            if (answerCheckerService.checkAnswer(question, answer)) {
                quizResult.incrementRightAnswersCount();
            }
        });
        return quizResult;
    }

    private User askUserName() {
        ioService.print(localeMessageService.getMessage(FIRST_NAME_QUESTION));
        User user = new User();
        user.setFirstName(ioService.getAnswer());
        ioService.print(localeMessageService.getMessage(LAST_NAME_QUESTION));
        user.setLastName(ioService.getAnswer());
        return user;
    }

}
