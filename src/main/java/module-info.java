module com.dam.filmotecalite {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dam.filmotecalite to javafx.fxml;
    exports com.dam.filmotecalite;
}