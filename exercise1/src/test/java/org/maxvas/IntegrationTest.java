package org.maxvas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.maxvas.domain.QuizResult;
import org.maxvas.service.IOService;
import org.maxvas.service.IOServiceImpl;
import org.maxvas.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class IntegrationTest {


    @MockBean
    private IOService ioService;

    @Autowired
    private QuizService quizService;


    @Test
    public void quiz() {

        doNothing().when(ioService).print(anyString());

        doAnswer(invocationOnMock -> "1").when(ioService).getAnswer();

        QuizResult quizResult = quizService.conductQuiz();

        Assertions.assertEquals(1, quizResult.getRightAnswers());
        Assertions.assertTrue(quizResult.isTestPassed());
    }

}
