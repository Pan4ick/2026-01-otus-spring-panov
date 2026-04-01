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
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.otus.hw.helpers.StringsStorage.MESSAGE_TO_ENTER;

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
    @DisplayName("сформировать класс TestResult с правильными ответами")
    void shouldMakeTestResultClassWithRightAnswers() {
        List<Question> questions = List.of(
                new Question("What is Java?",
                        List.of(new Answer("Programming language", true),
                                new Answer("SDK", false),
                                new Answer("I dont know", false))),
                new Question("What is JVM?",
                        List.of(new Answer("Java Virtual Machine", true),
                                new Answer("I dont know", false)))
        );

        Student student = new Student("Alex", "Panov", 12);

        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readStringWithPrompt(MESSAGE_TO_ENTER))
                .thenReturn("Programming language")
                .thenReturn("Java Virtual Machine");

        TestResult result = testService.executeTestFor(student);

        assertThat(result).isNotNull();
        assertThat(result.getStudent()).isEqualTo(student);
        assertThat(result.getAnsweredQuestions()).hasSize(2);
        assertThat(result.getRightAnswersCount()).isEqualTo(2);

        verify(ioService, times(2)).readStringWithPrompt(MESSAGE_TO_ENTER);
    }

    @Test
    @DisplayName("сформировать класс TestResult с неправильными ответами")
    void shouldMakeTestResultClassWithWrongAnswers() {
        List<Question> questions = List.of(
                new Question("What is Java?",
                        List.of(new Answer("Programming language", true),
                                new Answer("SDK", false),
                                new Answer("I dont know", false))),
                new Question("What is JVM?",
                        List.of(new Answer("Java Virtual Machine", true),
                                new Answer("I dont know", false)))
        );

        Student student = new Student("Alex", "Panov", 23);

        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readStringWithPrompt(MESSAGE_TO_ENTER))
                .thenReturn("I dont know")
                .thenReturn("Book");

        TestResult result = testService.executeTestFor(student);

        assertThat(result).isNotNull();
        assertThat(result.getStudent()).isEqualTo(student);
        assertThat(result.getAnsweredQuestions()).hasSize(2);
        assertThat(result.getRightAnswersCount()).isEqualTo(0);

        verify(ioService, times(2)).readStringWithPrompt(MESSAGE_TO_ENTER);
    }

}