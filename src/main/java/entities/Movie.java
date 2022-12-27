package entities;

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

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                '}';
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