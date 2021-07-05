package org.maxvas;

import org.maxvas.service.PrintQuestionsService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var service = context.getBean(PrintQuestionsService.class);
        service.printQuestions();
    }

}
