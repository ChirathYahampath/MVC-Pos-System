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
import dto.CustomerDTO;
import dto.tm.CustomerTm;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.util.List;

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


    private CustomerModel CustomerModel = new CustomerModelImpl();
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

        try {
            List<CustomerDTO> dtoList = CustomerModel.allCustomers();

            for (CustomerDTO dto:dtoList) {
                Button btn = new Button("Delete");

                CustomerTm c = new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                });

                tmList.add(c);
            }

            CustomerTable.setItems(tmList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id) {


        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadCustomerTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

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

    private void clearFields() {
        CustomerTable.refresh();
        txtSallery.clear();
        txtAddress.clear();
        txtName.clear();
        txtId.clear();
        txtId.setEditable(true);
    }

    public void SaveButtonOnAction(javafx.event.ActionEvent actionEvent) {

        try {
            boolean isSaved = CustomerModel.saveCustomer(new CustomerDTO(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSallery.getText())
            ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateButtonOnAction(javafx.event.ActionEvent actionEvent) {
        try {
            boolean isUpdated = CustomerModel.updateCustomer(new CustomerDTO(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSallery.getText())
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                loadCustomerTable();
                clearFields();
            }
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


