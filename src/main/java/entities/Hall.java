package entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return id == hall.id && capacity == hall.capacity && numberAvailableSeats == hall.numberAvailableSeats && numberOfSoldSeats == hall.numberOfSoldSeats && Objects.equals(attendance, hall.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, numberAvailableSeats, numberOfSoldSeats, attendance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("capacity", capacity)
                .append("numberAvailableSeats", numberAvailableSeats)
                .append("numberOfSoldSeats", numberOfSoldSeats)
                .append("attendance", attendance)
                .toString();
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
