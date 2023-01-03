package web.form.validation;

import web.form.AnaliseForm;
import web.form.SessionForm;

import java.time.LocalDate;
import java.time.LocalTime;

public class AnaliseFormValidator implements Validator<AnaliseForm> {

    private static final String NAME_REGEX = "^[A-Za-z0-9_-]{1,20}$";


    @Override
    public boolean validate(AnaliseForm form) {
        LocalDate currentDate = LocalDate.now();

        return dataValidate(form.getStartDate(), form.getEndDate()) || timeValidate(form.getStartTime(), form.getEndTime());
    }
}