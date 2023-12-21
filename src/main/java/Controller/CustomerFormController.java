package Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.tm.CustomerTm;
import javafx.scene.control.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class CustomerFormController {


    @FXML
    private AnchorPane Root;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtSallery;

    @FXML
    private TableView<CustomerTm> CustomerTable;

    @FXML
    private TableColumn collID;

    @FXML
    private TableColumn CollName;

    @FXML
    private TableColumn CollAdreds;

    @FXML
    private TableColumn CollSallery;

    @FXML
    private TableColumn CollOption;

    @FXML
    void ReloadButtnOnAction(ActionEvent event) {
        loadCustomerTable();
        CustomerTable.refresh();
        clearFields();

    }

    private void clearFields() {
        CustomerTable.refresh();
        txtSallery.clear();
        txtAddress.clear();
        txtId.clear();
        txtName.clear();
        txtId.setEditable(true);
    }

    @FXML
    void SaveButtonOnAction(ActionEvent event) {


    }

    public void initialize(){
        collID.setCellValueFactory(new PropertyValueFactory<>("id"));
       CollName.setCellValueFactory(new PropertyValueFactory<>("name"));
        CollAdreds.setCellValueFactory(new PropertyValueFactory<>("address"));
        CollSallery.setCellValueFactory(new PropertyValueFactory<>("salary"));
        CollOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        CustomerTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            SetData(newValue);
        });

    }

    private void SetData(CustomerTm newValue) {
        if (newValue != null) {
            txtId.setEditable(false);
            txtId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSallery.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        String sql = "Select * From Customer";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(sql);

            while(result.next()){
                Button btn = new Button("Delete");


                CustomerTm c =new CustomerTm(

                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getDouble(4),
                btn
            );
                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                });

                tmList.add(c);

            }

            connection.close();
           CustomerTable.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id) {
        String sql = " DELETE from customer WHERE id = '"+id+"'";


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);

            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted ! ").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Somthing went Wrong !").show();
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateButtonOnAction(ActionEvent event) {

    }

    @FXML
    public void ReloadButtnOnAction(javafx.event.ActionEvent actionEvent) {

            loadCustomerTable();
            CustomerTable.refresh();
            clearFields();
        }


    public void SaveButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Customer c = new Customer(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSallery.getText()));

        String sql = " INSERT INTO customer VALUES('"+c.getId()+"','"+c.getName()+"','"+c.getAddress()+"','"+c.getSalary()+"')";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            Statement stm = connection.createStatement();
            int result;
            result = stm.executeUpdate(sql);


            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Thank You.We have saved your information! ").show();
                loadCustomerTable();
                clearFields();
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Customer c = new Customer(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSallery.getText()));

        String sql = "UPDATE customer SET name='"+c.getName()+"', address='"+c.getAddress()+"', salary= "+c.getSalary()+" WHERE id='"+c.getId()+"'";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);

            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Thank You.We have Updated your information! ").show();
                loadCustomerTable();
                clearFields();
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void BackButtonOnAction(javafx.event.ActionEvent actionEvent) {
            Stage stage = (Stage) CustomerTable.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"))));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}


