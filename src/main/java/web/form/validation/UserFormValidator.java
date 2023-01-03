package web.form.validation;


import web.form.UserForm;

public class UserFormValidator implements Validator<UserForm> {

    private static final String LOGIN_REGEX = "^[A-Za-z0-9_-]{3,16}$";
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9_-]{5,18}$";


    @Override
    public boolean validate(UserForm form) {
        return stringParamValidate(form.getLogin(), LOGIN_REGEX)
                || stringParamValidate(form.getPassword(), PASSWORD_REGEX);
    }


}

