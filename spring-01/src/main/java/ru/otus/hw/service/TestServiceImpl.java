package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionDao dao;

    private final IOService ioService;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = dao.findAll();
        for (Question question : questions) {
            ioService.printLine(question.text());
            if (question.answers() != null) {
                for (Answer answer : question.answers()) {
                    ioService.printFormattedLine("- %s", answer.text());
                }
            }
            ioService.printLine("");
        }
    }
}
