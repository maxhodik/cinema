package validation;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import web.form.AnaliseForm;
import web.form.validation.AnaliseFormValidator;
import web.form.validation.Validator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnaliseFormValidationTest {
    Validator<AnaliseForm> validator = new AnaliseFormValidator();
    AnaliseForm form;
    static String[] datesCorrect1 = {"2022-01-15", "2023-01-13"};
    static String[] datesCorrect2 = {"2023-01-15", "2023-11-13"};
    static String[] datesCorrect3 = {"2020-01-15", "2023-01-13"};
    static String[] datesCorrect4 = {"2023-01-15", "2023-01-15"};
    static String[] datesCorrect5 = {"2022-01-15", "2022-01-23"};
    static String[] timesCorrect1 = {"01:15", "01:23"};
    static String[] timesCorrect2 = {"12:15", "17:40"};
    static String[] timesCorrect3 = {"12:05", "23:49"};
    static String[] timesCorrect4 = {"07:05", "13:49"};
    static String[] timesCorrect5 = {"09:00", "22:00"};
    static String[] moviesCorrect1 = {"pop", "Titanic 2", "Место встречи изменить нельзя  - 23"};
    static String[] moviesCorrect2 = {"Titanic", "Titanic 2", "Место встречи изменить нельзя  - 23"};
    static String[] moviesCorrect3 = {"I", "i I 43", "Три плюс два"};
    static String[] moviesCorrect4 = {"101", "King Kong жив 2., а путін сдох", "How are you???"};

    static String[] statusesCorrect1 = {"ACTIVE", "CANCELED"};
    static String[] statusesCorrect2 = {"CANCELED"};
    static String[] statusesCorrect3 = {"ACTIVE"};

    static String[] daysCorrect1 = {"0", "1", "2", "3", "4", "5", "6"};
    static String[] daysCorrect2 = {"0", "1", "2", "6"};
    static String[] daysCorrect3 = {"6"};
    static String[] daysCorrect4 = {"3", "4", "5", "6", "0",};
    static String[] daysCorrect5 = {"0"};
    static String[] datesWrong1 = {"2022-01-15", "2022-01-13"};
    static String[] datesWrong2 = {"2023-0-15", "2023-11-13"};
    static String[] datesWrong3 = {"2020-01-15", "20223-01-13"};

    static String[] timesWrong1 = {"21:15", "01:23"};
    static String[] timesWrong2 = {"12:15", "10:40"};
    static String[] timesWrong3 = {"7:05", "23:49"};

    static String[] moviesWrong1 = {"`", "!", "  - "};

    static String[] statusesWrong1 = {"ACTIgxVE", "CANCELED"};
    static String[] statusesWrong2 = {"CAfNCELED"};
    static String[] statusesWrong3 = {"2"};

    static String[] daysWrong1 = {"01", "1", "2", "3", "4", "5", "6"};
    static String[] daysWrong2 = {"Monday", "0", "2", "6"};
    static String[] daysWrong3 = {"8"};
    static String[] datesEmptyOrNull1 = {"2022-01-13", "2022-01-13"};
    static String[] datesEmptyOrNull2 = {"", " "};
    static String[] datesEmptyOrNull3 = null;
    static String[] datesEmptyOrNull4 = {};

    static String[] datesNull = null;
    static String[] datesEmpty = {};
    //
    static String[] timesNull = null;
    static String[] timesEmpty = {};

    static String[] moviesNull = null;
    static String[] moviesEmpty = {};


    static String[] statusesNull = null;
    static String[] statusesEmpty = {};

    static String[] daysNull = null;
    static String[] daysEmpty = {};


    @ParameterizedTest
    @MethodSource("provideCorrectArguments")
    public void validate(AnaliseForm form) {
        assertFalse(validator.validate(form));
    }


    public static Stream<Arguments> provideCorrectArguments() {
        return Stream.of(
                Arguments.of(createForm2(datesCorrect1, timesCorrect3, moviesCorrect3, statusesCorrect1, daysCorrect1)),
                Arguments.of(createForm2(datesCorrect2, timesCorrect4, moviesCorrect1, statusesCorrect3, daysCorrect2)),
                Arguments.of(createForm2(datesCorrect5, timesCorrect2, moviesCorrect4, statusesCorrect2, daysCorrect5)),
                Arguments.of(createForm2(datesCorrect4, timesCorrect1, moviesCorrect2, statusesCorrect3, daysCorrect3)),
                Arguments.of(createForm2(datesCorrect3, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull1, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull2, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull3, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull4, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4))
        );
    }

    @ParameterizedTest
    @MethodSource("provideWrongDateArguments")
    public void validateWrongDate(AnaliseForm form) {
        assertTrue(validator.validate(form));
    }


    public static Stream<Arguments> provideWrongDateArguments() {
        return Stream.of(
                Arguments.of(createForm2(datesWrong1, timesWrong1, moviesWrong1, statusesWrong2, daysWrong1)),
                Arguments.of(createForm2(datesWrong2, timesWrong2, moviesWrong1, statusesWrong1, daysWrong2)),
                Arguments.of(createForm2(datesWrong3, timesWrong3, moviesWrong1, statusesWrong3, daysWrong3)),
                Arguments.of(createForm2(datesWrong3, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesCorrect5, timesWrong2, moviesCorrect4, statusesCorrect2, daysCorrect5)),
                Arguments.of(createForm2(datesCorrect1, timesCorrect3, moviesWrong1, statusesCorrect1, daysCorrect1)),
                Arguments.of(createForm2(datesCorrect5, timesWrong2, moviesCorrect4, statusesWrong2, daysCorrect5)),
                Arguments.of(createForm2(datesCorrect5, timesCorrect5, moviesCorrect4, statusesCorrect2, daysWrong2)));
    }

    @ParameterizedTest
    @MethodSource("provideNullOrEmptyArguments")
    public void validateNullOrEmpty(AnaliseForm form) {
        assertFalse(validator.validate(form));
    }


    public static Stream<Arguments> provideNullOrEmptyArguments() {
        return Stream.of(
                Arguments.of(createForm2(datesCorrect1, timesCorrect3, moviesCorrect3, statusesCorrect1, daysNull)),
                Arguments.of(createForm2(datesCorrect2, timesCorrect4, moviesCorrect1, statusesCorrect3, datesEmpty)),
                Arguments.of(createForm2(datesCorrect5, timesCorrect2, moviesCorrect4, statusesNull, daysCorrect5)),
                Arguments.of(createForm2(datesCorrect4, timesCorrect1, moviesCorrect2, statusesEmpty, daysCorrect3)),
                Arguments.of(createForm2(datesCorrect3, timesCorrect5, moviesNull, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull1, timesCorrect5, moviesEmpty, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull2, timesNull, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmptyOrNull3, timesEmpty, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesEmpty, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesNull, timesCorrect5, moviesCorrect2, statusesCorrect1, daysCorrect4)),
                Arguments.of(createForm2(datesNull, timesNull, moviesNull, statusesNull, daysNull)),
                Arguments.of(createForm2(datesEmpty, timesEmpty, moviesEmpty, statusesEmpty, daysEmpty))
        );
    }

    public static AnaliseForm createForm2(String[] dates, String[] times, String[] movies, String[] statuses, String[] days) {
        return new AnaliseForm(dates, times, movies, statuses, days);
    }
}

