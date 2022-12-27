package dao.maper;

import entities.Role;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder().id(rs.getInt("users.id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .role(Role.getByNameIgnoringCase(rs.getString("role"))).build();
    }
}
