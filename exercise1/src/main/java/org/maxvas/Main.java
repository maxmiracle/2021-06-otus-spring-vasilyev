package org.maxvas;

import org.maxvas.service.QuizService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(QuizConfiguration.class);
        var service = context.getBean(QuizService.class);
        service.conductQuiz();
    }
}
