package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppProperties implements TestConfig, TestFileNameProvider, StudentAgeConfig {

    private int rightAnswersCountToPass;

    private String testFileName;

    private int maxStudentAge;

    private int minStudentAge;

    public AppProperties(@Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass,
                         @Value("${test.fileName}") String testFileName, @Value("${student.maxAge}") int maxStudentAge,
                         @Value("${student.minAge}") int minStudentAge) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.testFileName = testFileName;
        this.maxStudentAge = maxStudentAge;
        this.minStudentAge = minStudentAge;
    }
}
