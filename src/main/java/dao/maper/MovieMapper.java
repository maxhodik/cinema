package dao.maper;

import entities.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements ObjectMapper<Movie> {

    @Override
    public Movie extractFromResultSet(ResultSet rs) throws SQLException {
        return Movie.builder().id(rs.getInt("movies.id"))
                .name(rs.getString("name"))
                .build();
    }
}
