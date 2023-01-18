package entities;

import java.util.Objects;

public class Movie {
    private int id;

    private String name;
    private Status status;

    public Movie(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.status=builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && Objects.equals(name, movie.name) && status == movie.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    public static class Builder {

        public Status status;
        private int id;

        private String name;


        public int getId() {
            return id;

        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder status (Status status){
            this.status=status;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}