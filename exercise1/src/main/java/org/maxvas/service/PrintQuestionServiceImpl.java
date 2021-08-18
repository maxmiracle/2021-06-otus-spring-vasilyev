package org.maxvas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maxvas.domain.AnswerType;
import org.maxvas.domain.Question;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class PrintQuestionServiceImpl implements PrintQuestionService {

    private final IOService ioService;

    public void printQuestion(Question question) {
        String questionMsg = String.format("%d. %s", question.getNumber(), question.getQuestion());
        ioService.print(questionMsg);
        if (question.getAnswerType().equals(AnswerType.OPTIONS)) {
            var options = question.getOptions();
            for (var i = 1; i <= options.size(); i++) {
                printOption(i, options.get(i - 1));
            }
        }
    }

    private void printOption(int i, String s) {
        ioService.print(String.format("\t%d) %s", i, s));
    }
}
