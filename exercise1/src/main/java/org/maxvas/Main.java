package org.maxvas;

import org.maxvas.service.MarkService;
import org.maxvas.service.QuizService;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@Configuration
@ComponentScan
public class Main {

    @Bean
    @Scope("prototype")
    public MarkService getMarkService()
    {
        return new MarkService();
    }

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        var service = context.getBean(QuizService.class);
        service.conductQuiz();
    }
}
