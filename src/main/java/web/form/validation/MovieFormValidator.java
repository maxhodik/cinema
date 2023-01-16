package web.form.validation;

import web.form.MovieForm;

public class MovieFormValidator implements Validator<MovieForm> {
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яіїєґ0-9_]+[A-zA-zА-Яа-яіїєґ0-9_ '-`!?.,-:;]*";


    @Override
    public boolean validate(MovieForm form) {

        return stringParamValidate(form.getName(), NAME_REGEX) ;
    }
}
