package web.form.validation;

import web.form.SessionForm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class SessionFormValidator implements Validator<SessionForm> {
    private static final LocalTime startTime = LocalTime.parse("09:00");
    private static final LocalTime endTime = LocalTime.parse("22:00");
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яіїєґ0-9_]+[A-zA-zА-Яа-яіїєґ0-9_ '-`!?.,-:;]*";
    private static final String REGEX_NUMBER = "[0-9]{1}[0-9]*";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String TIME_REGEX = "^(((([0-1][0-9])|(2[0-3])):?[0-5][0-9]+$))";

    private int minNumberOfSeats = 1;
    private int maxNumberOfSeats = 100;

    @Override
    public boolean validate(SessionForm form) {
        return validateString(form) ||
                validateByCurrentDataTime(form);
    }


    private boolean validateByCurrentDataTime(SessionForm form) throws DateTimeParseException {
        int capacity = Integer.parseInt(form.getCapacity());
        LocalDate date = LocalDate.parse(form.getSessionDate());
        LocalTime time = LocalTime.parse(form.getSessionTime());
//        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentDataTime = LocalDateTime.now();
        LocalDate currentDate = LocalDate.from(currentDataTime);
        LocalDateTime formDataTime = LocalDateTime.of(date, time);
        if (currentDataTime.isAfter(formDataTime)) {
            return true;
        }
        return dataValidate(currentDate, date) || timeValidate(startTime, time) || timeValidate(time, endTime)
                || numberParamValid(capacity, minNumberOfSeats, maxNumberOfSeats);
    }


    private boolean validateString(SessionForm form) {
        return stringParamValidate(form.getSessionId(), REGEX_NUMBER) || stringParamValidate(form.getCapacity(), REGEX_NUMBER)
                || stringParamValidate(form.getSessionDate(), DATE_REGEX) || stringParamValidate(form.getSessionTime(), TIME_REGEX)
                || stringParamValidate(form.getMovieName(), NAME_REGEX);
    }


}


