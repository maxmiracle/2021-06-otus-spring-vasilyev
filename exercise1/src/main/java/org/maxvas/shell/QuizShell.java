package org.maxvas.shell;

import lombok.AllArgsConstructor;
import org.maxvas.service.QuizService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class QuizShell {

    private final QuizService quizService;

    @ShellMethod(value = "Start quiz", key = {"start", "quiz", "q"})
    public String startQuiz() {
        quizService.conductQuiz();
        return "Quiz finished";
    }

}
