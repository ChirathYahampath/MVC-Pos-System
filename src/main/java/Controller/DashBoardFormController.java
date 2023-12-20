package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.namespace.QName;
import java.io.IOException;

public class DashBoardFormController {
    public JFXButton CustomerBtn;
    public AnchorPane Pane;

    public void CustomerButtonOnAction(ActionEvent actionEvent) {
        Stage stage= (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/CustomerForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
