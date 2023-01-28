package validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.form.OrderForm;
import web.form.validation.OrderValidator;
import web.form.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OrderFormValidatorTest {
    Validator<OrderForm> validator = new OrderValidator();
    OrderForm form;


    @ParameterizedTest
    @ValueSource(strings = {
          "1","2","3","4","10","11","9", "19", "20"})
    public void validate(String seats) {
        form = new OrderForm(seats);
        assertFalse(validator.validate(form));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1","2.02","3.09","-4","0","-11.3","98", "-20", "21", "hhdf", "12d"})
    public void validateNotCorrect(String seats) {
        form = new OrderForm(seats);
        assertTrue(validator.validate(form));
    }


    @Test
    public void validateNulls() {
        form = new OrderForm(null);
        assertTrue(validator.validate(form));
    }

    @Test
    public void validateEmpty() {
        form = new OrderForm("");
        assertTrue(validator.validate(form));
    }
}

