package com.example.dsaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add options to the choiceBox
        choiceBox.getItems().addAll("Fewest Stops", "Shortest Route", "Shortest with fewest changes");
        imageView.setImage(new Image(getClass().getResource("StationMapWithContent.png").toExternalForm()));
    }

    public void choiceBoxOptions(ActionEvent actionEvent) throws IOException {

    }
}