package org.maxvas.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maxvas.dao.Question;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class QuestionReaderService implements QuestionReader {

    private final CsvReaderProvider csvReaderProvider;

    @Override
    public List<Question> readQuestions() throws IOException {
        try (var reader = csvReaderProvider.getReader()) {

            CsvToBean<Question> csvToBean = new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .withSeparator(';')
                    .build();
            return csvToBean.parse();
        }
    }
}
