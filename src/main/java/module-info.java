module com.example.dsaca2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.dsaca2 to javafx.fxml;
    exports com.example.dsaca2;
}