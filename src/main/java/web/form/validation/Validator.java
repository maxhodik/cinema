package web.form.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

import static java.util.Objects.isNull;

@FunctionalInterface
public interface Validator<T> {


    boolean validate(T form);

    default boolean stringParamValidate(String param, String regex) {
        return isNull(param) || !param.matches(regex) ;
    }

    default boolean numberParamValid(int param, int minValue, int maxValue) {
        return param < minValue || param > maxValue;
    }
    default boolean idNumberParamValid (int param, int minValue){
        return param < minValue;
    }
    default boolean dataValidate (LocalDate start, LocalDate end){
        return end.isBefore(start);
    }
    default boolean timeValidate (LocalTime start, LocalTime end) {
        return end.isBefore(start) ;


    }
}
