package dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class SessionAdminDto {
    private int id;
    private String movieName;
    private LocalDate date;
    private LocalTime time;

    private int numberOfAvailableSeats;
    private int numberOfSoldSeats;
    private DayOfWeek dayOfWeek;
    private int capacity;
    private BigDecimal attendance;

    public SessionAdminDto(int id, String movieName, LocalDate date, LocalTime time, int numberOfAvailableSeats, int capacity) {
        this.id = id;
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.dayOfWeek = date.getDayOfWeek();
        this.capacity = capacity;
        this.numberOfSoldSeats = capacity - numberOfAvailableSeats;
        BigDecimal attendance1 = new BigDecimal((float) numberOfSoldSeats / capacity * 100);
        this.attendance = attendance1.setScale(2, RoundingMode.HALF_UP);

    }

    public int getNumberOfSoldSeats() {
        return numberOfSoldSeats;
    }

    public void setNumberOfSoldSeats(int numberOfSoldSeats) {
        this.numberOfSoldSeats = numberOfSoldSeats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getAttendance() {
        return attendance;
    }

    public void setAttendance(BigDecimal attendance) {
        this.attendance = attendance;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
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
