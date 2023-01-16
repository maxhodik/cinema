package validation;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import web.form.UserForm;
import web.form.validation.UserFormValidator;
import web.form.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserFormValidatorTest {
    Validator<UserForm> validator = new UserFormValidator();
    UserForm form;


    @ParameterizedTest
    @CsvSource(value = {
            "Admin, Admin",
            "brian, brian35",
            "charles, Charles40",
            "Max, Max123",
            "JoMlk, Max123",
            "n98Y, 12546"})
    public void validate(String name, String password) {
        form = new UserForm(name, password);
        assertFalse(validator.validate(form));
    }


    @ParameterizedTest
    @CsvSource(value = {
            "Admin, min",
            "brian, an35",
            "charles, Cha",
            "Max, M253"})
    public void validateNotCorrectPassword(String name, String password) {
        form = new UserForm(name, password);
        assertTrue(validator.validate(form));
    }

    @ParameterizedTest
    @CsvSource(value = {
            ("mn, Admin"),
            "12, brian35",
            "CG, Charles40",
            "ИГОрь, Max123",
            "стаС, Hy254"})
    public void validateNotCorrectLogin(String name, String password) {
        form = new UserForm(name, password);
        assertTrue(validator.validate(form));
    }


    @Test
    public void validateNulls() {
        form = new UserForm(null, null);
        assertTrue(validator.validate(form));
    }

    @Test
    public void validateEmpty() {
        form = new UserForm("", "");
        assertTrue(validator.validate(form));
    }
}