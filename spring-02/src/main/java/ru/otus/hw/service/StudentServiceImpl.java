package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Student;

import static ru.otus.hw.helpers.StringsStorage.AGE_ERROR_MESSAGE;
import static ru.otus.hw.helpers.StringsStorage.AGE_INPUT;
import static ru.otus.hw.helpers.StringsStorage.FIRST_NAME_INPUT;
import static ru.otus.hw.helpers.StringsStorage.SECOND_NAME_INPUT;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final AppProperties appProperties;

    private final IOService ioService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPrompt(FIRST_NAME_INPUT);
        var lastName = ioService.readStringWithPrompt(SECOND_NAME_INPUT);
        var age = ioService.readIntForRangeWithPrompt(appProperties.getMinStudentAge(),
                appProperties.getMaxStudentAge(), AGE_INPUT, AGE_ERROR_MESSAGE);
        return new Student(firstName, lastName, age);
    }
}
