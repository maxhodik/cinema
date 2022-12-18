package dao.maper;

import entities.Hall;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HallMapper implements ObjectMapper<Hall>{
    @Override
    public Hall extractFromResultSet(ResultSet rs) throws SQLException {
        return Hall.builder().id(rs.getInt("halls.id"))
                .numberAvailableSeats(rs.getInt("number_available_seats"))
                .numberSeats(rs.getInt("number_seats")).build();
    }
}
