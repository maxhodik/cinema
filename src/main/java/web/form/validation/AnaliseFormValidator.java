package web.form.validation;

import dto.MovieDto;
import entities.Movie;
import entities.Role;
import entities.Status;
import entities.WeekDays;
import web.form.AnaliseForm;
import web.form.SessionForm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AnaliseFormValidator implements Validator<AnaliseForm> {

    private static final LocalTime startTime = LocalTime.parse("09:00");
    private static final LocalTime endTime = LocalTime.parse("22:00");
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яіїєґ0-9_]+[A-zA-zА-Яа-яіїєґ0-9_ '-`!?.,-:;]*";
    private static final String REGEX_NUMBER = "[0-6]";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String TIME_REGEX = "^(((([0-1][0-9])|(2[0-3])):?[0-5][0-9]+$))";
    private static WeekDays weekDays;
    private static Status status;


    @Override
    public boolean validate(AnaliseForm form) {
        boolean result = false;
        String[] movies = form.getMovies();
        if (!isNullOrEmpty(movies)) {
            for (String movie : movies) {
                result = result || stringParamValidate(movie, NAME_REGEX);
            }
        }
        String[] dates = form.getDates();
        String[] times = form.getTimes();
        result = result || validateData(dates, DATE_REGEX) || validateTime(times, TIME_REGEX)
                || validateDays(form.getDays())
                || validateStatus(form.getStatuses());
        return result;
    }

    private boolean validateTime(String[] times, String regex) {
        if (isNullOrEmpty(times)) {
            return false;
        }
        String timeStart = times[0];
        String timeEnd = times[1];
        if (timeStart.trim().isEmpty() && timeStart.trim().equals(timeEnd.trim())) {
            return false;
        }
        if (!validateParam(times, regex)) {
            return validateByTime(times);
        } else return true;
    }

    private boolean validateData(String[] date, String regex) {
        if (isNullOrEmpty(date)) {
            return false;
        }
        String timeStart = date[0];
        String timeEnd = date[1];
        if (timeStart.trim().isEmpty() && timeStart.trim().equals(timeEnd.trim())) {
            return false;
        }
        if (!validateParam(date, regex)) {
            return validateByDate(date);
        } else return true;
    }

    private boolean validateDays(String[] days) {
        if (isNullOrEmpty(days)) return false;
        boolean contains = false;
        for (String day : days) {
            contains = contains || stringParamValidate(day, REGEX_NUMBER);
        }
        return contains;
    }


    private boolean validateStatus(String[] statuses) {
        if (isNullOrEmpty(statuses)) return false;
        boolean contains = false;
        for (String stat : statuses) {
            contains = contains || !Status.contains(stat);
        }
        return contains;
    }


    private boolean validateByTime(String[] times) throws DateTimeParseException {
        if (isNullOrEmpty(times)) return false;
        LocalTime timeStart = LocalTime.parse(times[0]);
        LocalTime timeEnd = LocalTime.parse(times[1]);
        return timeValidate(timeStart, timeEnd);
    }

    private boolean validateByDate(String[] dates) throws DateTimeParseException {
        if (isNullOrEmpty(dates)) return false;
        LocalDate dateStart = LocalDate.parse(dates[0]);
        LocalDate dateEnd = LocalDate.parse(dates[1]);
        return dataValidate(dateStart, dateEnd);
    }


//    private boolean validateString(AnaliseForm form) {
//        return validateParam(form.getDates(), DATE_REGEX) ||
//                validateParam(form.getTimes(), TIME_REGEX)
//                || validateParam(form.getMovies(), NAME_REGEX);
//
//    }

    private boolean validateParam(String[] params, String regex) {
        boolean result = false;
        if (isNullOrEmpty(params)) return false;
        for (String param : params) {
            result = result || stringParamValidate(param, regex);
        }
        return result;

    }

    private static boolean isNullOrEmpty(String[] params) {
        if (params == null || params.length == 0) {
            return true;
        }
        return false;
    }


}
