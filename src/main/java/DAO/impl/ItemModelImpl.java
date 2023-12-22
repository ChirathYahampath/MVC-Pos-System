package DAO.impl;

import DAO.ItemModel;
import db.DBConnection;
import dto.itemDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


    public class ItemModelImpl implements ItemModel {
        @Override
        public boolean saveItem(itemDTO dto) {
            return false;
        }

        @Override
        public boolean updateItem(itemDTO dto) {
            return false;
        }

        @Override
        public boolean deleteItem(String code) {
            return false;
        }

        @Override
        public itemDTO getItem(String code) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM item WHERE code=?";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,code);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                return new itemDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
            }
            return null;
        }

        @Override
        public List<itemDTO> allItems() throws SQLException, ClassNotFoundException {
            List<itemDTO> list = new ArrayList<>();
            String sql = "SELECT * FROM item";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()){
                list.add(new itemDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }
            return list;
        }
    }


