package web.form;

import entities.Movie;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionForm {

    private String sessionId;
    private String sessionDate;
    private String sessionTime;

    private String movieName;
    private String capacity;


    public SessionForm(String sessionId, String sessionDate, String sessionTime, String movieName, String capacity) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.movieName = movieName;
        this.capacity = capacity;

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

}