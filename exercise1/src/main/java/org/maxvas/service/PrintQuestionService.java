package org.maxvas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maxvas.dao.AnswerType;
import org.maxvas.dao.Question;

@Slf4j
@AllArgsConstructor
public class PrintQuestionService implements PrintQuestion {

    public void printQuestion(Question question)
    {
        System.out.printf("%d. %s%n", question.getNumber(), question.getQuestion());
        if (question.getAnswerType().equals(AnswerType.OPTIONS))
        {
            var options = question.getOptions();
            for(var i = 1; i<= options.size();i++)
            {
                printOption(i, options.get(i - 1));
            }
        }
    }

    private void printOption(int i, String s) {
        System.out.printf("\t%d) %s%n", i, s);
    }
}