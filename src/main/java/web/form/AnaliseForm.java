package web.form;

import entities.Hall;
import entities.Session;

import java.time.LocalDate;
import java.time.LocalTime;

public class AnaliseForm {
    private String[] dates;
    private String[] times;
    private String[] movies;
    private String[] statuses;
    private String[] days;


    public AnaliseForm(String[] dates, String[] times, String[] movies, String[] statuses, String[] days) {
        this.dates = dates;
        this.times = times;
        this.movies = movies;
        this.statuses = statuses;
        this.days = days;
    }

    public AnaliseForm(Builder builder) {
        this.dates = builder.dates;
        this.times = builder.times;
        this.movies = builder.movies;
        this.statuses = builder.statuses;
        this.days = builder.days;

    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }

    public String[] getMovies() {
        return movies;
    }

    public void setMovies(String[] movies) {
        this.movies = movies;
    }

    public String[] getStatuses() {
        return statuses;
    }

    public void setStatuses(String[] statuses) {
        this.statuses = statuses;
    }

    public String[] getDays() {
        return days;
    }
    public void setDays(String[] days) {
        this.days = days;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String[] dates;
        private String[] times;
        private String[] movies;
        private String[] statuses;
        private String[] days;

        public Builder dates(String[] dates) {
            this.dates = dates;
            return this;
        }
        public Builder times(String[] times) {
            this.times = times;
            return this;
        }
        public Builder movies (String[] times) {
            this.movies = movies;
            return this;
        }
        public Builder statuses (String[] times) {
            this.statuses = statuses;
            return this;
        }
        public Builder days (String[] times) {
            this.days = days;
            return this;
        }

        public AnaliseForm build() {
            return new AnaliseForm(this);
        }


    }
}
