package org.maxvas.dao;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class Question {

    @CsvBindByName
    private Integer number;

    @CsvBindByName
    private String question;

    @CsvBindByName
    private AnswerType answerType;

    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",")
    private List<String> options;

    @CsvBindByName
    private Integer rightAnswerIndex;

}
