package entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Order {
    private int id;
    private User user;
    private Session session;
    private State state;
    private int numberOfSeats;
    private int price;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("user", user)
                .append("session", session)
                .append("state", state)
                .append("numberOfSeats", numberOfSeats)
                .append("price", price)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Order(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.session = builder.session;
        this.state = builder.state;
        this.numberOfSeats = builder.numberOfSeats;
        this.price= builder.price;
    }

    public static class Builder{
        private int id;
        private User user;
        private Session session;
        private State state;
        private int numberOfSeats;
        private int price;

        public Builder id (int id){
            this.id=id;
            return this;
        }
        public Builder user (User user){
            this.user=user;
            return this;
        }
        public Builder session (Session session){
            this.session=session;
            return  this;
        }
        public Builder state (State state){
            this.state= state;
            return this;
        }
        public Builder  numberOfSeats (int numberOfSeats){
            this.numberOfSeats=numberOfSeats;
            return this;
        }
        public Builder price (int price){
            this.price=price;
            return this;
        }
        public Order build (){return new Order(this);}


    }

}
