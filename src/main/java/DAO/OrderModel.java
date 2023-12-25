package DAO;

import dto.OrderDTO;

import java.sql.SQLException;

public interface OrderModel {
    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;
    OrderDTO lastOrder() throws SQLException, ClassNotFoundException;
}
