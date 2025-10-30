package com.dam.filmotecalite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("FilmotecaLite");
        stage.setResizable(false);
        stage.setWidth(400);
        stage.setHeight(300);
        stage.setScene(scene);
        stage.show();
    }
}
