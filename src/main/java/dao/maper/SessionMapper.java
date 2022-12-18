package dao.maper;

import entities.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper implements ObjectMapper<Session> {
    @Override
    public Session extractFromResultSet(ResultSet rs) throws SQLException {
        return Session.builder().id(rs.getInt("id"))
                .data(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime()).build();
    }
}
