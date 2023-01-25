package validation;

import dao.impl.SqlUserDao;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import web.form.OrderForm;
import web.form.SessionForm;
import web.form.UserForm;
import web.form.validation.OrderFormValidator;
import web.form.validation.SessionFormValidator;
import web.form.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

public class SessionFormValidationTest {
    Validator<SessionForm> validator = new SessionFormValidator();
    SessionForm form;
    private final LocalDateTime DATA_TIME = LocalDateTime.of(2023, Month.JANUARY, 15, 20, 0, 0);

    @ParameterizedTest
    @CsvSource(value = {
            "10, 2024-01-15, 10:15, Aladdin, 40",
            "245, 2023-01-22, 19:20, Duna, 28",
            "348, 2024-02-18, 09:00, Вий, 100",
            "0, 2023-02-28, 20:00, King kong, 100",
            "0, 2023-03-15, 19:59, KISS, 99"})
    public void validate(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        form = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        try (MockedStatic<LocalDateTime> utilities = Mockito.mockStatic(LocalDateTime.class)) {
            utilities.when(LocalDateTime::now).thenReturn(DATA_TIME);
            utilities.when(() -> LocalDateTime.of(any(), any())).thenCallRealMethod();
            assertEquals(DATA_TIME, LocalDateTime.now());
            assertFalse(validator.validate(form));
        }


    }

    @ParameterizedTest
    @CsvSource(value = {
            "2.6, 2024-01-15, 10:15, Aladdin, 40",
            "d, 2023-01-22, 19:20, Duna, 28",
            "3d, 2024-02-18, 09:00, Вий, 100",
            "hg, 2023-02-28, 20:00, King kong, 100",
            "-20, 2023-03-15, 19:59, KISS, 990"})
    public void validateWrongId(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        form = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        assertTrue(validator.validate(form));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10, 2023-1-15, 10:15, Aladdin, 40",
            "245, 2023-0122, 19:20, Duna, 28",
            "348, 2024.01-30, 09:00, Вий, 100",
            "0, 2023-12,-28, 20:00, King kong, 100",
            "0, 20 23-03-15, 19:59, KISS, 99"})
    public void validateWrongDate(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        form = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        assertTrue(validator.validate(form));

    }

    @ParameterizedTest
    @CsvSource(value = {
            "10, 2023-01-15, -10:15, Aladdin, 40",
            "245, 2023-01-22, 19-20, Duna, 28",
            "348, 2024-02-18, 9:00, Вий, 100",
            "0, 2023-02-28, 22:01, King kong, 100",
            "0, 2023-03-15, 08:59, KISS, 99"})
    public void validateWrongTime(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        form = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        assertTrue(validator.validate(form));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10, 2023-01-15, 10:15, !, 40",
            "245, 2023-01-22, 19:20, +, 28",
            "348, 2024-02-18, 09:00, ), 100",
            "0, 2023-02-28, 20:00, ., 100",
            "0, 2023-03-15, 19:59,:, 99"})
    public void validateWrongMovieName(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        form = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        assertTrue(validator.validate(form));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10, 2023-01-15, 10:15, Aladdin, 140",
            "245, 2023-01-22, 19:20, Duna, 0",
            "348, 2024-02-18, 09:00, Вий, -3",
            "0, 2023-02-28, 20:00, King kong, 15.5",
            "0, 2023-03-15, 19:59, KISS, 9+9"})
    public void validateWrongCapacity(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        form = new SessionForm(sessionId, sessionDate, sessionTime, movieName, capacity);
        assertTrue(validator.validate(form));
    }
}
