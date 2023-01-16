package validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.form.MovieForm;
import web.form.UserForm;
import web.form.validation.MovieFormValidator;
import web.form.validation.UserFormValidator;
import web.form.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieFormValidatorTest {
    Validator<MovieForm> validator = new MovieFormValidator();
    MovieForm movieForm;


    @ParameterizedTest
    @ValueSource(strings = {"12321", "pop", "Titanic 2", "Место встречи изменить нельзя  - 23",
            "I", "i I 43", "Три плюс два", "Тіні зібутих предків", "Нехай у наших ворогів у горлі пір'я поросте",
    "101", "King Kong жив 2., а путін сдох", "How are you???"})
    public void validate(String name) {
        movieForm= new MovieForm(name);
        assertFalse(validator.validate(movieForm));
    }
    @ParameterizedTest
    @ValueSource(strings = {"!!!", "~", "`", "...", "?", "'овфао"})
    public void notValidate(String name) {
        movieForm= new MovieForm(name);
        assertTrue(validator.validate(movieForm));
    }
    @Test
    public void validateNulls() {
        movieForm= new MovieForm(null);
        assertTrue(validator.validate(movieForm));
    }

    @Test
    public void validateEmpty() {
        movieForm= new MovieForm("");
        assertTrue(validator.validate(movieForm));
    }
}

