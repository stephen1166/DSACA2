package com.example.dsaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ImageView mapView;

    private File tempFile;

    private Image detailedMap;

    private Image simpleMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add options to the choiceBox
        choiceBox.getItems().addAll("Fewest Stops", "Shortest Route", "Shortest with fewest changes");
        detailedMap = new Image(getClass().getResourceAsStream("StationMapWithContent.png"));
        mapView.setImage(detailedMap);
    }

    public void loadMap(ActionEvent actionEvent){
        mapView.setImage(detailedMap);
        System.out.println("this does work, you are not crazy");
    }

    public void choiceBoxOptions(ActionEvent actionEvent) throws IOException {

    }
}