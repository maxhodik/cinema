package dto;

import entities.Status;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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
    private Status status;

    public SessionAdminDto(int id, String movieName, LocalDate date,
                           LocalTime time, int numberOfAvailableSeats, int numberOfSoldSeats,
                           int capacity, BigDecimal attendance, Status status) {
        this.id = id;
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfSoldSeats = numberOfSoldSeats;
        this.capacity = capacity;
        this.attendance = attendance;
        this.status = status;
    }

    public SessionAdminDto(SessionAdminDto.Builder builder) {
        this.id = builder.id;
        this.movieName = builder.movieName;
        this.date = builder.date;
        this.time = builder.time;
        this.numberOfAvailableSeats = builder.numberOfAvailableSeats;
        this.numberOfSoldSeats = builder.numberOfSoldSeats;
        this.dayOfWeek = builder.dayOfWeek;
        this.capacity = builder.capacity;
        this.attendance = builder.attendance;
        this.status = builder.status;


    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getNumberOfSoldSeats() {
        return numberOfSoldSeats;
    }

    public void setNumberOfSoldSeats(int numberOfSoldSeats) {
        this.numberOfSoldSeats = numberOfSoldSeats;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAdminDto that = (SessionAdminDto) o;
        return id == that.id && numberOfAvailableSeats == that.numberOfAvailableSeats && numberOfSoldSeats == that.numberOfSoldSeats && capacity == that.capacity && status == that.status && Objects.equals(movieName, that.movieName) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(attendance, that.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, id, movieName, date, time, numberOfAvailableSeats, numberOfSoldSeats, capacity, attendance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("id", id)
                .append("movieName", movieName)
                .append("date", date)
                .append("time", time)
                .append("numberOfAvailableSeats", numberOfAvailableSeats)
                .append("numberOfSoldSeats", numberOfSoldSeats)
                .append("dayOfWeek", dayOfWeek)
                .append("capacity", capacity)
                .append("attendance", attendance)
                .toString();
    }

    public class Builder {

        private int id;
        private String movieName;
        private LocalDate date;
        private LocalTime time;

        private int numberOfAvailableSeats;
        private int numberOfSoldSeats;
        private DayOfWeek dayOfWeek;
        private int capacity;
        private BigDecimal attendance;
        private Status status;

        public SessionAdminDto.Builder id(int id) {
            this.id = id;
            return this;
        }

        public SessionAdminDto.Builder movieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public SessionAdminDto.Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public SessionAdminDto.Builder time(LocalTime time) {
            this.time = time;
            return this;
        }

        public SessionAdminDto.Builder numberOfAvailableSeats(int numberOfAvailableSeats) {
            this.numberOfAvailableSeats = numberOfAvailableSeats;
            return this;
        }

        public SessionAdminDto.Builder numberOfSoldSeats(int numberOfSoldSeats) {
            this.numberOfSoldSeats = numberOfSoldSeats;
            return this;
        }

        public SessionAdminDto.Builder dayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public SessionAdminDto.Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }
        public SessionAdminDto.Builder attendance(BigDecimal attendance) {
            this.attendance = attendance;
            return this;
        }
        public SessionAdminDto.Builder status(Status status) {
            this.status = status;
            return this;
        }

    }
}
