package DAO;

import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException;

    CustomerDto searchCustomer(String id);

    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
}
