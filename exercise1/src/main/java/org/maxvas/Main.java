package org.maxvas;

import lombok.AllArgsConstructor;
import org.maxvas.service.QuizService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@AllArgsConstructor
public class Main implements CommandLineRunner {
    private final QuizService quizService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        quizService.conductQuiz();
    }
}
