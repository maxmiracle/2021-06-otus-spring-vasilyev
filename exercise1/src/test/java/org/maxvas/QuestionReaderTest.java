package org.maxvas;

import org.junit.jupiter.api.Test;
import org.maxvas.service.CsvReaderProviderFromResourceFile;
import org.maxvas.service.QuestionReaderService;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class QuestionReaderTest {

    private final static String TEST_CSV = "number;question;answerType;options\n1;Test options?;OPTIONS;One,Two,3,4,5";

    @Test
    public void questionReaderTest() throws IOException {
        var csvDataProviderMock = Mockito.mock(CsvReaderProviderFromResourceFile.class);
        when(csvDataProviderMock.getReader()).thenReturn(new StringReader(TEST_CSV));
        var questionReader = new QuestionReaderService(csvDataProviderMock);

        var list = questionReader.readQuestions();
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("3", list.get(0).getOptions().get(2));
    }
}
