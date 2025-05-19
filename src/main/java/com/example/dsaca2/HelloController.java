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
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private ChoiceBox<String> searchOption;
    @FXML
    private ChoiceBox<String> startingStop;
    @FXML
    private ChoiceBox<String> endStop;
    @FXML
    private ChoiceBox<String> avoidStop;
    @FXML
    private ImageView mapView;

    private File tempFile;

    private Image detailedMap;

    private Image simpleMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add options to the choiceBox
        searchOption.getItems().addAll("Fewest Stops", "Shortest Route", "Shortest with fewest changes");
    }

    public void detailedMap(ActionEvent actionEvent) throws IOException {
        detailedMap = new Image(getClass().getResource("/Images/StationMapWithContent.png").openStream());
        mapView.setImage(detailedMap);
    }

    public void simpleMap(ActionEvent actionEvent) throws IOException {
        simpleMap = new Image(getClass().getResource("/Images/StationMapBlank.png").openStream());
        mapView.setImage(simpleMap);
    }

    public void choiceBoxOptions(ActionEvent actionEvent) throws IOException {
        if (searchOption.getValue().equals("Fewest Stops")) {
            String startingStop = startingStop.getValue();
        }
    }

    public void csvReader() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(getClass().getResource("/data/vienna_subway.csv"))));
        String line = "";

        while((line = csvReader.readLine()) != null) {
            System.out.println(line);
            String[] data = line.split(",");
            System.out.println("starting destination " + data[0] + "ending destination" + data[1]);
        }
    }
}