package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@DisplayName("CsvQuestionDaoTest должен")
class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @Test
    @DisplayName("корректно читать CSV файл из ресурсов")
    void shouldCorrectlyReadCsvFile() {
        String testFileName = "questions.csv";
        given(fileNameProvider.getTestFileName()).willReturn(testFileName);

        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(testFileName);
        assertThat(resourceStream).as("Файл %s должен существовать", testFileName).isNotNull();

        List<Question> questions = csvQuestionDao.findAll();

        Question firstQuestion = questions.get(0);
        assertThat(firstQuestion.text())
                .as("Текст первого вопроса")
                .isEqualTo("Is there life on Mars?");

        assertThat(firstQuestion.answers())
                .as("Количество ответов на первый вопрос")
                .hasSize(3);

        List<Answer> firstAnswers = firstQuestion.answers();

        assertThat(firstAnswers.get(0).text())
                .as("Первый ответ на первый вопрос")
                .isEqualTo("Science doesn't know this yet");
        assertThat(firstAnswers.get(0).isCorrect())
                .as("Первый ответ должен быть правильным")
                .isTrue();

        assertThat(firstAnswers.get(1).text())
                .as("Второй ответ на первый вопрос")
                .isEqualTo("Certainly. The red UFO is from Mars. And green is from Venus");
        assertThat(firstAnswers.get(1).isCorrect())
                .as("Второй ответ должен быть неправильным")
                .isFalse();

        assertThat(firstAnswers.get(2).text())
                .as("Третий ответ на первый вопрос")
                .isEqualTo("Absolutely not");
        assertThat(firstAnswers.get(2).isCorrect())
                .as("Третий ответ должен быть неправильным")
                .isFalse();

        Question secondQuestion = questions.get(1);
        assertThat(secondQuestion.text())
                .as("Текст второго вопроса")
                .isEqualTo("How should resources be loaded form jar in Java?");

        assertThat(secondQuestion.answers())
                .as("Количество ответов на второй вопрос")
                .hasSize(3);

        List<Answer> secondAnswers = secondQuestion.answers();

        assertThat(secondAnswers.get(0).text())
                .as("Первый ответ на второй вопрос")
                .isEqualTo("ClassLoader#geResourceAsStream or ClassPathResource#getInputStream");
        assertThat(secondAnswers.get(0).isCorrect())
                .as("Первый ответ должен быть правильным")
                .isTrue();

        assertThat(secondAnswers.get(1).text())
                .as("Второй ответ на второй вопрос")
                .isEqualTo("ClassLoader#geResource#getFile + FileReader");
        assertThat(secondAnswers.get(1).isCorrect())
                .as("Второй ответ должен быть неправильным")
                .isFalse();

        assertThat(secondAnswers.get(2).text())
                .as("Третий ответ на второй вопрос")
                .isEqualTo("Wingardium Leviosa");
        assertThat(secondAnswers.get(2).isCorrect())
                .as("Третий ответ должен быть неправильным")
                .isFalse();

        Question thirdQuestion = questions.get(2);
        assertThat(thirdQuestion.text())
                .as("Текст третьего вопроса")
                .isEqualTo("Which option is a good way to handle the exception?");

        assertThat(thirdQuestion.answers())
                .as("Количество ответов на третий вопрос")
                .hasSize(4);

        List<Answer> thirdAnswers = thirdQuestion.answers();

        assertThat(thirdAnswers.get(0).text())
                .as("Первый ответ на третий вопрос")
                .isEqualTo("@SneakyThrow");
        assertThat(thirdAnswers.get(0).isCorrect())
                .as("Первый ответ должен быть неправильным")
                .isFalse();

        assertThat(thirdAnswers.get(1).text())
                .as("Второй ответ на третий вопрос")
                .isEqualTo("e.printStackTrace()");
        assertThat(thirdAnswers.get(1).isCorrect())
                .as("Второй ответ должен быть неправильным")
                .isFalse();

        assertThat(thirdAnswers.get(2).text())
                .as("Третий ответ на третий вопрос")
                .isEqualTo("Rethrow with wrapping in business exception (for example, QuestionReadException)");
        assertThat(thirdAnswers.get(2).isCorrect())
                .as("Третий ответ должен быть правильным")
                .isTrue();

        assertThat(thirdAnswers.get(3).text())
                .as("Четвертый ответ на третий вопрос")
                .isEqualTo("Ignoring exception");
        assertThat(thirdAnswers.get(3).isCorrect())
                .as("Четвертый ответ должен быть неправильным")
                .isFalse();
    }

    @Test
    @DisplayName("выбрасывать QuestionReadException, когда файл не найден")
    void shouldThrowQuestionReadExceptionWhenFileNotFound() {
        given(fileNameProvider.getTestFileName()).willReturn("nonexistent-file.csv");

        assertThatThrownBy(() -> csvQuestionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessageContaining("File not found!")
                .hasMessageContaining("nonexistent-file.csv");
    }

}