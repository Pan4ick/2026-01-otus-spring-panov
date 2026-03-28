package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
@Data
public class AppProperties implements TestConfig, TestFileNameProvider, StudentAgeConfig {

    @Value("${test.rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    @Value("${test.fileName}")
    private String testFileName;

    @Value("${student.maxAge}")
    private int maxStudentAge;

    @Value("${student.minAge}")
    private int minStudentAge;
}
