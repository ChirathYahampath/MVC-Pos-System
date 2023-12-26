package DAO;

import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    List<CustomerDTO> allCustomers() throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id);

    boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
}
