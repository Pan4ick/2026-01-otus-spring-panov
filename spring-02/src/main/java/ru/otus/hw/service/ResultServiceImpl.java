package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.TestResult;

import static ru.otus.hw.helpers.StringsStorage.*;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine(EMPTY_LINE);
        ioService.printLine(TEST_RESULTS_OUTPUT);
        ioService.printFormattedLine(STUDENT_OUTPUT, testResult.getStudent().getFullName());
        ioService.printFormattedLine("Answered questions count: %d", testResult.getAnsweredQuestions().size());
        ioService.printFormattedLine("Right answers count: %d", testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine(PASSED_TEST_OUTPUT);
            return;
        }
        ioService.printLine(FAILED_TEST_OUTPUT);
    }
}
