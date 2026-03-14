package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TestServiceImpl должен")
public class TestServiceImplTest {
    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    @DisplayName("выводить все вопросы с ответами, если они есть")
    void shouldPrintAllQuestionsWithAnswers() {
        List<Question> questions = List.of(
                new Question("What is Java?",
                        List.of(new Answer("Programming language", true),
                                new Answer("Platform", true))),
                new Question("What is JVM?",
                        List.of(new Answer("Java Virtual Machine", true)))
        );
        given(questionDao.findAll()).willReturn(questions);

        testService.executeTest();

        verify(ioService, times(3)).printLine("");
        verify(ioService).printFormattedLine("Please answer the questions below%n");
        verify(ioService).printLine("What is Java?");
        verify(ioService).printLine("What is JVM?");
        verify(ioService).printFormattedLine("- %s", "Programming language");
        verify(ioService).printFormattedLine("- %s", "Platform");
        verify(ioService).printFormattedLine("- %s", "Java Virtual Machine");
    }

    @Test
    @DisplayName("корректно обрабатывать вопросы без ответов")
    void shouldHandleQuestionsWithoutAnswers() {
        Question questionWithoutAnswers = new Question("Question without answers?", null);
        given(questionDao.findAll()).willReturn(List.of(questionWithoutAnswers));

        testService.executeTest();

        verify(ioService, times(2)).printLine("");
        verify(ioService).printFormattedLine("Please answer the questions below%n");
        verify(ioService).printLine("Question without answers?");
        verify(ioService, never()).printFormattedLine(eq("- %s"), any());
    }

}