module com.example.dsaca2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens com.example.dsaca2 to javafx.fxml, org.junit.jupiter.api, org.mockito;
    exports com.example.dsaca2;
}
