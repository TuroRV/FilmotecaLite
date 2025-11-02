package com.dam.filmotecalite;

import com.dam.filmotecalite.dao.UserDAO;
import com.dam.filmotecalite.dao.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    UserDAO userDAO = new UserDAOImpl();

    @FXML
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void login(ActionEvent actionEvent) {
        String userNickname = nicknameField.getText();
        String password = passwordField.getText();

        User user = userDAO.getUserByNickname(userNickname);
        if (user == null ){
            showAlert(Alert.AlertType.WARNING, "Error de autenticación", "Usuario no encontrado", "Por favor, ingrese un usuario registrado.");

        } else if (user.getUser_nickname().equals(userNickname) && user.getUser_password().equals(password)) {
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            try {
                Session.setUser(user);

                Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));

                Scene scene = new Scene(root, 1080, 720);

                stage.setTitle("Películas");
                stage.setScene(scene);
                stage.setWidth(1080);
                stage.setHeight(720);
                stage.setResizable(true);
                stage.centerOnScreen();
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            showAlert(Alert.AlertType.WARNING, "Error de autenticación", "Credenciales incorrectas", "El nickname o la contraseña no son válidos.");

    }
    }

}
