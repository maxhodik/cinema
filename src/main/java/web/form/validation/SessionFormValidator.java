package web.form.validation;

import web.form.SessionForm;

import java.time.LocalDate;
import java.time.LocalTime;


public class SessionFormValidator implements Validator<SessionForm> {
    private static final LocalTime startTime = LocalTime.parse("09:00");
    private static final LocalTime endTime = LocalTime.parse("22:00");
    private static final String NAME_REGEX = "^[A-Za-z0-9_-]{1,20}$";
    private int minNumberOfSeats = 1;
    private int maxNumberOfSeats = 100;

    @Override
    public boolean validate(SessionForm form) {
        LocalDate currentDate = LocalDate.now();

        return dataValidate(currentDate, form.getDate()) || timeValidate(startTime, form.getTime()) || timeValidate(form.getTime(), endTime )
                || numberParamValid(form.getCapacity(), minNumberOfSeats, maxNumberOfSeats);
    }
}
