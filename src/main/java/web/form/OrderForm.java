package web.form;

public class OrderForm {
    private String numberOfSeats;

    public OrderForm(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
