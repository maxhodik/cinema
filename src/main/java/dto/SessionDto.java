package dto;

import entities.Status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class SessionDto {
    private int id;
    private String movieName;
    private LocalDate date;
    private LocalTime time;
    private Status status;
    private int numberOfSeats;
    private DayOfWeek dayOfWeek;

    public SessionDto(int id, String movieName, LocalDate date, LocalTime time, Status status, int numberOfSeats) {
        this.id = id;
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.numberOfSeats = numberOfSeats;

    }

//    public SessionDto(int id, String movieName, LocalDate date, LocalTime time) {
//        this.id = id;
//        this.movieName = movieName;
//        this.date = date;
//        this.time = time;
//
//    }




    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDayOfWeek (DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
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
