package web.form.validation;

import web.form.OrderForm;

public class OrderFormValidator implements Validator<OrderForm> {
    private int minNumberOfSeats=1;
    private int maxNumberOfSeats=20;

    @Override
    public boolean validate(OrderForm form) {
        return numberParamValid(form.getNumberOfSeats(),minNumberOfSeats,maxNumberOfSeats);
    }
}
