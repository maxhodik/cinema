package web.form.validation;

import web.form.MovieForm;

public class MovieFormValidator implements Validator<MovieForm>{
//    private static final String NAME_REGEX = "^[[A-Za-zА-Яа-я0-9_]{1+} ]*";
    private static final String NAME_REGEX = "[А-Яа-я]*";
    @Override
    public boolean validate(MovieForm form) {
        return stringParamValidate(form.getName(), NAME_REGEX);
    }
}
