package dto;

import java.util.Objects;

public class MovieDto {
    private int id;
    private String name;

    public MovieDto(int id, String name) {
        this.id = id;
        this.name = name;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return id == movieDto.id && Objects.equals(name, movieDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
