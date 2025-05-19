package com.example.dsaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
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

    private Image detailedMap;

    private Image simpleMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add options to the choiceBox
        searchOption.getItems().addAll("Fewest Stops", "Shortest Route", "Shortest with fewest changes");
        try {
            csvReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void detailedMap(ActionEvent actionEvent) throws IOException {
        detailedMap = new Image(getClass().getResource("/Images/StationMapWithContent.png").openStream());
        mapView.setImage(detailedMap);
    }

    public void simpleMap(ActionEvent actionEvent) throws IOException {
        simpleMap = new Image(getClass().getResource("/Images/StationMapBlank.png").openStream());
        mapView.setImage(simpleMap);
    }

//    public void choiceBoxOptions(ActionEvent actionEvent) throws IOException {
//        if (searchOption.getValue().equals("Fewest Stops")) {
//            String startingStop = startingStop.getValue();
//        }
//    }

    public void csvReader() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/vienna_subway.csv");

        BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        Set<String> stations = new TreeSet<>();

        while ((line = csvReader.readLine()) != null) {
            System.out.println(line);
            String[] data = line.split(",");
            System.out.println("Start = " + data[0] + " End = " + data[1]);

            stations.add(data[0]);
            stations.add(data[1]);
        }

        startingStop.getItems().setAll(stations);
        endStop.getItems().setAll(stations);
        avoidStop.getItems().setAll(stations);
    }

    public void exit(ActionEvent event)
    {
        System.out.println("exit");
        System.exit(-1);
    }
}