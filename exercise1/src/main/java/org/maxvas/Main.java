package org.maxvas;

import org.maxvas.service.QuizService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var service = context.getBean(QuizService.class);
        service.conductQuiz();
    }

}
