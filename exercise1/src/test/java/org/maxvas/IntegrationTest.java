package org.maxvas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.maxvas.conf.QuizConfiguration;
import org.maxvas.domain.QuizResult;
import org.maxvas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static org.mockito.Mockito.*;

@SpringBootTest
@EnableConfigurationProperties(QuizConfiguration.class)
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

    @Configuration
    @Import({QuizService.class, QuestionReaderService.class, QuizResultsAssessorImpl.class, LocaleMessageService.class,
            CsvReaderProviderFromResourceFile.class, UserResultFormatterImpl.class, PrintQuestionServiceImpl.class,
            AnswerCheckerServiceImpl.class})
    public static class IntegrationTestConfiguration {
        @Bean
        public MessageSource messageSource() {
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:messages");
            messageSource.setCacheSeconds(10); //reload messages every 10 seconds
            return messageSource;
        }
    }

}
