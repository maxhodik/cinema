package entities;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Session {
    private int id;
    private Movie movie;
    private Hall hall;
    private LocalDate date;
    private LocalTime time;
    private Status status;


    public Session(int id, Movie movie, Hall hall, LocalDate date, LocalTime time, Status status) {
        this.id = id;
        this.movie = movie;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.status = status;

    }

    public Session(Builder builder) {
        this.id = builder.id;
        this.movie = builder.movie;
        this.hall = builder.hall;
        this.date = builder.date;
        this.time = builder.time;
        this.status=builder.status;

    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("movie", movie)
                .append("hall", hall)
                .append("date", date)
                .append("time", time)
                .append("status", status)
                .toString();
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        private Status status;


        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder movie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Builder hall(Hall hall) {
            this.hall = hall;
            return this;
        }

        public Builder data(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder dayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder time(LocalTime time) {
            this.time = time;
            return this;

        }
        public Builder status (Status status){
            this.status=status;
            return this;
        }

        public Session build() {
            return new Session(this);
        }
    }
}

