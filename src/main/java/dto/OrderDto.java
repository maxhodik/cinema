package dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class OrderDto {
    private int id;
    private int numberOfSeats;
    private int price;
    private String login;
    private int count;

    public OrderDto(int id, int numberOfSeats, int price, String login) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.login = login;
     count=price*numberOfSeats;

    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && numberOfSeats == orderDto.numberOfSeats && price == orderDto.price && count == orderDto.count && Objects.equals(login, orderDto.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfSeats, price, login, count);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("numberOfSeats", numberOfSeats)
                .append("price", price)
                .append("login", login)
                .append("count", count)
                .toString();
    }
}
