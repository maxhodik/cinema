package dao.maper;

import entities.Order;
import entities.State;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements ObjectMapper<Order>{

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return Order.builder().id(rs.getInt("id"))
                .state(State.getByNameIgnoringCase(rs.getString("orders.state")))
                .numberOfSeats(rs.getInt("number_of_seats"))
                .price(rs.getInt("price")).build();
    }
}
