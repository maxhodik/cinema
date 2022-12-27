package entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Hall {
    private int id;
    private int capacity;
    private int numberAvailableSeats;
    private int numberOfSoldSeats;
    private BigDecimal attendance;


    public Hall(Builder builder) {
        this.id = builder.id;
        this.numberAvailableSeats = builder.numberAvailableSeats;
        this.capacity = builder.capacity;
        this.numberOfSoldSeats = builder.numberOfSoldSeats;
        this.attendance = builder.attendance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumberAvailableSeats() {
        return numberAvailableSeats;
    }

    public void setNumberAvailableSeats(int numberAvailableSeats) {
        this.numberAvailableSeats = numberAvailableSeats;
    }

    public int getNumberOfSoldSeats() {
        return numberOfSoldSeats;
    }

    public void setNumberOfSoldSeats(int numberOfSoldSeats) {
        this.numberOfSoldSeats = numberOfSoldSeats;
    }

    public BigDecimal getAttendance() {
        return attendance;
    }

    public void setAttendance(BigDecimal attendance) {
        this.attendance = attendance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private int capacity;
        private int numberAvailableSeats;
        private int numberOfSoldSeats;

        private BigDecimal attendance;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder numberSeats(int capacity) {
            this.capacity = capacity;

            return this;
        }

        public Builder numberAvailableSeats(int numberAvailableSeats) {
            this.numberAvailableSeats = numberAvailableSeats;
            return this;
        }

        public Builder numberOfSoldSeats(int numberOfSoldSeats) {
            this.numberOfSoldSeats = numberOfSoldSeats;
            ;
            return this;
        }

        public Builder attendance(BigDecimal attendance) {

            this.attendance = attendance;
            return this;
        }

        public Hall build() {
            return new Hall(this);
        }


    }

}
