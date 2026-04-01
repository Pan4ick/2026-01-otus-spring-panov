package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static ru.otus.hw.helpers.StringsStorage.EMPTY_LINE;
import static ru.otus.hw.helpers.StringsStorage.MESSAGE_TO_ENTER;
import static ru.otus.hw.helpers.StringsStorage.TEST_MESSAGE;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine(EMPTY_LINE);
        ioService.printFormattedLine(TEST_MESSAGE);
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        for (var question : questions) {
            ioService.printLine(question.text());
            List<Answer> answers = question.answers();
            if (answers == null || answers.isEmpty()) {
                continue;
            }
            for (Answer answer : question.answers()) {
                ioService.printFormattedLine("- %s", answer.text());
            }
            String answerStr = ioService.readStringWithPrompt(MESSAGE_TO_ENTER);
            var isAnswerValid = question.answers().stream()
                    .anyMatch(answer -> answer.isCorrect() && answer.text().equals(answerStr));
            ioService.printLine(EMPTY_LINE);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
