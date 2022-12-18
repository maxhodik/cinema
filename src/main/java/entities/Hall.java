package entities;

import java.util.Objects;

public class Hall {
    private int id;
    private int numberSeats;
    private int numberAvailableSeats;
    public Hall (Builder builder){
        this.id=builder.id;
        this.numberAvailableSeats= builder.numberAvailableSeats;
        this.numberSeats=builder.numberSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public int getNumberAvailableSeats() {
        return numberAvailableSeats;
    }

    public void setNumberAvailableSeats(int numberAvailableSeats) {
        this.numberAvailableSeats = numberAvailableSeats;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "numberSeats=" + numberSeats +
                ", numberAvailableSeats=" + numberAvailableSeats +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private int numberSeats;
        private int numberAvailableSeats;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder numberSeats(int numberSeats) {
            this.numberSeats = numberSeats;
            return this;
        }

        public Builder numberAvailableSeats(int numberAvailableSeats) {
            this.numberAvailableSeats = numberAvailableSeats;
            return this;
        }


        public Hall build() {
            return new Hall(this);
        }
    }


}
