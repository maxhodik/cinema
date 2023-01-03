package web.form;

import entities.Movie;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionForm {
    private String movieName;
    private int capacity;
    private LocalDate date;
    private LocalTime time;

    public SessionForm(String movieName, int capacity, LocalDate date, LocalTime time) {
        this.movieName = movieName;
        this.capacity = capacity;
        this.date = date;
        this.time = time;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
