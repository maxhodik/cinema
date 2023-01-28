package web.form.validation;

import web.form.OrderForm;

public class OrderValidator implements Validator<OrderForm> {

    private static final String REGEX_NUMBER = "[1-9]{1}[0-9]*";

    private int minNumberOfSeats=1;
    private int maxNumberOfSeats=20;

    @Override
    public boolean validate(OrderForm form) {
        return  stringParamValidate(form.getNumberOfSeats(), REGEX_NUMBER) ||
         numberParamValid(Integer.parseInt(form.getNumberOfSeats()),minNumberOfSeats,maxNumberOfSeats);
    }
}
