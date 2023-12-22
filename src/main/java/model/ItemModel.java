package model;

import dto.itemDTO;


import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    boolean saveItem(itemDTO dto);
    boolean updateItem(itemDTO dto);
    boolean deleteItem(String code);
    itemDTO getItem(String code) throws SQLException, ClassNotFoundException;
    List<itemDTO> allItems() throws SQLException, ClassNotFoundException;
}
