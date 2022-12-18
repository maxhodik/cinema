package entities;


import java.time.*;

public class Session {
    private int id;
    private Movie movie;
    private Hall hall;
    private LocalDate date;
    private LocalTime time;


    public Session(Builder builder) {
        this.id = builder.id;
        this.movie = builder.movie;
        this.hall = builder.hall;
        this.date = builder.date;
        this.time = builder.time;

    }


    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", movie=" + movie +
                ", hall=" + hall +
                ", date=" + date +
                ", time=" + time +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private int id;
        private Movie movie;
        private Hall hall;
        private LocalDate date;
        private DayOfWeek dayOfWeek;

        private LocalTime time;

        public Builder id (int id) {
            this.id = id;
            return this;
        }

        public Builder movie (Movie movie) {
            this.movie = movie;
            return this;
        }

        public Builder hall (Hall hall) {
            this.hall = hall;
            return this;
        }

        public Builder data (LocalDate date) {
            this.date = date;
            return this;
        }
        public Builder dayOfWeek (DayOfWeek dayOfWeek){
            this.dayOfWeek=dayOfWeek;
            return this;
        }

        public Builder time (LocalTime time) {
            this.time = time;
            return this;

        }
        public Session build (){return new Session(this);}
    }
}
