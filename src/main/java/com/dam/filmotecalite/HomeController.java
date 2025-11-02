package com.dam.filmotecalite;

import com.dam.filmotecalite.dao.MovieDAO;
import com.dam.filmotecalite.dao.MovieDAOImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    MovieDAO movieDAO = new MovieDAOImpl();
    private boolean showingFavorites = true;
    @FXML
    private TableView<Movie> tableView;

    @FXML
    Label usuarioLabel;
    @FXML
    Button mostrarFavoritasButton;
    @FXML
    Button mostrarFilmotecaButton;

    @FXML
    TableView<Movie> peliculasTableView;
    @FXML
    TableColumn<Movie,Integer> idColumn;
    @FXML
    TableColumn<Movie,String> tituloColumn;
    @FXML
    TableColumn<Movie,String> generoColumn;
    @FXML
    TableColumn<Movie,String> directorColumn;
    @FXML
    TableColumn<Movie,Integer> duracionColumn;
    @FXML
    TableColumn<Movie,String> ratingColumn;

    @FXML
    Label idLabel;
    @FXML
    TextField tituloField;
    @FXML
    TextField generoField;
    @FXML
    TextField directorField;
    @FXML
    TextField duracionField;
    @FXML
    TextField ratingField;
    @FXML
    Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    Button updateButton;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleAdd(ActionEvent actionEvent) {
        String title = tituloField.getText();
        String genero = generoField.getText();
        String director = directorField.getText();
        String duracion = duracionField.getText();
        String rating = ratingField.getText();

        if (title.isEmpty() || genero.isEmpty() || director.isEmpty() || duracion.isEmpty() || rating.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, rellena todos los campos");
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(duracion);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Duración inválida", "Introduce un número válido para la duración.");
            return;
        }

        Movie existing = movieDAO.getMovieByTitle(title);
        int userId = Session.getUser().getUser_id();

        if (existing != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Película existente");
            alert.setHeaderText("La película ya existe en la base de datos.");
            alert.setContentText("¿Deseas añadirla a tus películas?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    movieDAO.addMovieToUser(userId, existing.getId());
                    cargarPeliculasUsuario();
                    resetFields(null);
                }
            });

        } else {
            Alert confirmCreate = new Alert(Alert.AlertType.CONFIRMATION);
            confirmCreate.setTitle("Crear nueva película");
            confirmCreate.setHeaderText("La película no existe en la base de datos.");
            confirmCreate.setContentText("¿Deseas crearla y añadirla a tus películas?");
            confirmCreate.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            confirmCreate.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    Movie nueva = new Movie(title, genero, director, duration, rating);
                    int newId = movieDAO.addMovie(nueva);
                    if (newId != -1) {
                        movieDAO.addMovieToUser(userId, newId);
                        cargarPeliculasUsuario();
                        resetFields(null);
                        showAlert(Alert.AlertType.INFORMATION, "Película creada", "La película se ha creado y añadido correctamente.");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "No se pudo añadir la película.");
                    }
                }
            });
        }
    }

    public void handleUpdate(ActionEvent actionEvent) {
        Movie selected = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING,"Sin selección","Selecciona una película para poder actualizar sus datos");
        }

        String title = tituloField.getText();
        String genero = generoField.getText();
        String director = directorField.getText();
        String duracion = duracionField.getText();
        String rating = ratingField.getText();

        if (title.isEmpty() || genero.isEmpty() || director.isEmpty() || duracion.isEmpty() || rating.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, rellena todos los campos.");
            return;
        }
        int duration;
        try {
            duration = Integer.parseInt(duracion);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING,"Duración inválida","Por favor, introduce un número válido para la duración");
        }

        if (showingFavorites) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar actualización");
            confirm.setHeaderText("¿Deseas actualizar esta película?");
            confirm.setContentText("Se sobrescribirán los datos actuales.");
            confirm.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {

                    Movie updated = new Movie(selected.getId(), title,genero,director,Integer.parseInt(duracion),rating);

                    movieDAO.updateMovie(updated);
                    cargarPeliculasUsuario();
                    resetFields(null);
                    showAlert(Alert.AlertType.INFORMATION,"Actualizada","Película actualizada correctamente");
                }
            });
        }
        else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar actualización");
            confirm.setHeaderText("¿Deseas actualizar esta película de la base de datos?");
            confirm.setContentText("Se sobrescribirán los datos de la película. Esta acción no se puede deshacer");
            confirm.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {

                    Movie updated = new Movie(selected.getId(), title,genero,director,Integer.parseInt(duracion),rating);

                    movieDAO.updateMovieFromDatabase(updated);
                    loadAllMovies();
                    resetFields(null);
                    showAlert(Alert.AlertType.INFORMATION,"Actualizada","Película actualizada de la base de datos correctamente");
                }
            });
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        Movie selectedMovie = peliculasTableView.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showAlert(Alert.AlertType.ERROR,"Error de selección","Seleccione una película a eliminar");
        }
        else {
            if (showingFavorites) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmar borrado");
                confirm.setHeaderText("¿Deseas borrar esta película?");
                confirm.setContentText("Se eliminará la película de tu lista, podrás volver a añadirla más adelante");
                confirm.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        movieDAO.deleteMovieFromFavorites(selectedMovie,Session.getUser().getUser_id());
                        cargarPeliculasUsuario();

                        resetFields(null);
                        showAlert(Alert.AlertType.INFORMATION,"Película eliminada","Se ha eliminado la película correctamente");
                    }
                });
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmar borrado");
                confirm.setHeaderText("¿Deseas borrar esta película?");
                confirm.setContentText("Se eliminará la película de la base de datos, esta acción no se puede deshacer");
                confirm.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        movieDAO.deleteMovieFromDatabase(selectedMovie);
                        loadAllMovies();
                        resetFields(null);
                        showAlert(Alert.AlertType.INFORMATION,"Película eliminada","Se ha eliminado la película pemanentemente");
                    }
                });
            }


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usuarioLabel.setText("Usuario Actual: " + Session.getUser().getUser_nickname());

        peliculasTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarDatosPelicula(newSelection);
            }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        generoColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        duracionColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));


        cargarPeliculasUsuario();
        showingFavorites = true;

    }

    private void mostrarDatosPelicula(Movie newSelection) {
        idLabel.setText(String.valueOf(newSelection.getId()));
        tituloField.setText(newSelection.getTitle());
        generoField.setText(newSelection.getGenre());
        directorField.setText(newSelection.getDirector());
        duracionField.setText(String.valueOf(newSelection.getDuration()));
        ratingField.setText(String.valueOf(newSelection.getRating()));
    }

    private void cargarPeliculasUsuario() {
        int userid = Session.getUser().getUser_id();
        ArrayList<Movie> userMovies = movieDAO.getUserMovies(userid);
        peliculasTableView.setItems(FXCollections.observableArrayList(userMovies));
    }

    public void resetFields(ActionEvent actionEvent) {
        idLabel.setText("");
        tituloField.setText("");
        generoField.setText("");
        directorField.setText("");
        duracionField.setText("");
        ratingField.setText("");

        peliculasTableView.getSelectionModel().clearSelection();

    }

    public void mostrarFavoritas(ActionEvent actionEvent) {
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        showingFavorites = true;
        cargarPeliculasUsuario();
    }

    public void mostrarFilmoteca(ActionEvent actionEvent) {
        if (!Session.getUser().isUser_isadmin()) {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        }
        showingFavorites = false;
        loadAllMovies();
    }

    private void loadAllMovies() {
        ArrayList<Movie> movies = movieDAO.getAllMovies();
        peliculasTableView.setItems(FXCollections.observableArrayList(movies));
        showingFavorites = false;
    }

    public void handleLogout(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Logout");
        confirm.setContentText("¿Deseas cerrar la sesión actual?");
        confirm.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {

                try {
                    Session.setUser(null);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
                    stage.setScene(new Scene(root));
                    stage.setTitle("FilmotecaLite");
                    stage.setWidth(400);
                    stage.setHeight(300);
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
