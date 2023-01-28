package web.form.validation;

import web.form.IdForm;

public class IdValidator implements Validator <IdForm> {
    private static final String REGEX_NUMBER = "[0-9]{1}[0-9]*";
    private static final int MIN=0;

    @Override
    public boolean validate(IdForm form) {
        return  stringParamValidate(form.getId(), REGEX_NUMBER) ||
                idNumberParamValid(Integer.parseInt(form.getId()),MIN);
    }
}
