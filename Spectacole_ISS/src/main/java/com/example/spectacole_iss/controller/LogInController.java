package com.example.spectacole_iss.controller;

import com.example.spectacole_iss.HelloApplication;
import com.example.spectacole_iss.model.User;
import com.example.spectacole_iss.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.boot.model.process.internal.UserTypeMutabilityPlanAdapter;

import java.io.IOException;

public class LogInController {

    private Service service;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField parolaField;
    @FXML
    private RadioButton managerRB;
    @FXML
    private RadioButton spectatorRB;

    public void setService(Service service) {
        this.service = service;
    }


    public void onLogIn(ActionEvent actionEvent) {

        String username = usernameField.getText();
        String parola = parolaField.getText();
        User.Type option = null;
        if (managerRB.isSelected()) {
            option = User.Type.MANAGER;
        } else if (spectatorRB.isSelected()) {
            option = User.Type.SPECTATOR;
        }
        System.out.println(option);
        if (username == "" || username == null || parola == "" || parola == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username sau parola invalide!\n");
            alert.showAndWait();
            return;
        }
        if (option == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Trebuie sa bifati una din optiunile de mai jos!\n");
            alert.showAndWait();
            return;
        }

        User loggedUser = this.service.cautaUser(username, parola, option);
        if (loggedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User inexistent!\n");
            alert.showAndWait();
            return;
        } else if (loggedUser != null && loggedUser.getType() == User.Type.MANAGER) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Logare efectuata cu succes!\n");
            alert.showAndWait();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("spectacole.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 700);
                SpectacolController controller = fxmlLoader.getController();
                controller.setService(this.service);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                return;
            } catch (IOException ioException) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("An error has occurred!");
                alert.setContentText(ioException.getMessage());
                alert.showAndWait();
            }
        } else if (loggedUser != null && loggedUser.getType() == User.Type.SPECTATOR) {
            //TODO : instantiaza controller ul de spectator
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Logare efectuata cu succes!\n");
            alert.showAndWait();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("spectator.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 700);
                SpectatorController controller = fxmlLoader.getController();
                controller.setLoggedUserID(loggedUser.getId());
                controller.setService(this.service);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                return;
            } catch (IOException ioException) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("An error has occurred!");
                alert.setContentText(ioException.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("Eroare!");
            alert.showAndWait();
        }

    }
}
