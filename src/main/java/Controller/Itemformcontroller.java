package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.CustomerDTO;
import dto.itemDTO;
import dto.tm.CustomerTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import dto.tm.ItemTm;

import java.io.IOException;
import java.sql.*;
import java.util.function.Predicate;

public class Itemformcontroller {

    @FXML
    private JFXTextField TxtCode;

    @FXML
    private JFXTextField TxtDescription;

    @FXML
    private JFXTextField TxtUnitPrice;

    @FXML
    private JFXTextField TxtQuantity;

    @FXML
    private Button updatebtn;

    @FXML
    private Button Savebtn;

    @FXML
    private Button bckBttn;

    @FXML
    private JFXTreeTableView<ItemTm> itemTable;

    @FXML
    private TreeTableColumn Colitem;

    @FXML
    private TreeTableColumn colDescriotion;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private TreeTableColumn ColQuantity;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TextField txtSearch;


    public void initialize(){
        Colitem.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDescriotion.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        ColQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                itemTable.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getDesc().contains(newValue);
                    }
                });
            }
        });
    }


    @FXML
    void BackBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) itemTable.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void SaveButtonOnAction(ActionEvent event) {

        itemDTO dto = new itemDTO(TxtCode.getText(),
                TxtDescription.getText(),
                Double.parseDouble(TxtUnitPrice.getText()),
                Integer.parseInt(TxtQuantity.getText())
        );
        String sql = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getCode());
            pstm.setString(2,dto.getDesc());
            pstm.setDouble(3,dto.getUnitPrice());
            pstm.setInt(4,dto.getQty());
            int result = pstm.executeUpdate();
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                loadItemTable();
//                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        String sql = "Select * From item";

        try {

            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while(result.next()){
                JFXButton btn = new JFXButton("Delete");



                ItemTm tm =new ItemTm(

                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteItem(tm.getCode());
                });

                tmList.add(tm);

            }

            TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            itemTable.setRoot(treeItem);
            itemTable.setShowRoot(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {
       // String sql = "DELETE from item WHERE code = ?";
        //String sql = " DELETE FROM item WHERE code = ? ";
        String sql="DELETE FROM item WHERE code =?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,code);
            int result = pstm.executeUpdate();
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void UpdateButtonOnAction(ActionEvent event) {
        itemDTO dto = new itemDTO(TxtCode.getText(),
                TxtDescription.getText(),
                Double.parseDouble(TxtUnitPrice.getText()),
                Integer.parseInt(TxtQuantity.getText())
        );

        String sql = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm  = DBConnection.getInstance().getConnection().prepareStatement(sql);
            int result = pstm.executeUpdate();

            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Thank You.We have Updated your information! ").show();
                loadItemTable();
         //       clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}
