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

    // FXML-injected UI components
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

    // Images for map display
    private Image detailedMap;
    private Image simpleMap;

    // List to hold graph nodes representing stations
    private ArrayList<GraphNodeAL> nodesAl = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the search option choice box
        searchOption.getItems().addAll("Fewest Stops", "Shortest Route", "Shortest with fewest changes");
        startingStop.getItems().add("");
        startingStop.getSelectionModel().select(0);
        avoidStop.getItems().add("");
        avoidStop.getSelectionModel().select(0);
        endStop.getItems().add("");
        endStop.getSelectionModel().select(0);

        try {
            // Read station data from CSV and set up the map
            csvReader();
            detailedMap = new Image(getClass().getResource("/Images/StationMapWithContent.png").openStream());
            mapView.setImage(detailedMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Handler to switch to detailed map view
    public void detailedMap(ActionEvent actionEvent) throws IOException {
        detailedMap = new Image(getClass().getResource("/Images/StationMapWithContent.png").openStream());
        mapView.setImage(detailedMap);
    }

    // Handler to switch to simplified map view
    public void simpleMap(ActionEvent actionEvent) throws IOException {
        simpleMap = new Image(getClass().getResource("/Images/StationMapBlank.png").openStream());
        mapView.setImage(simpleMap);
    }

//    Placeholder for future logic based on selected search option
//    public void choiceBoxOptions(ActionEvent actionEvent) throws IOException {
//        if (searchOption.getValue().equals("Fewest Stops")) {
//            String startingStop = startingStop.getValue();
//        }
//    }

    // Reads station data from a CSV file and builds a graph
    public void csvReader() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/vienna_subway.csv");
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        ArrayList<String> stations = new ArrayList<>();

        // Read each line from the CSV and extract station pairs
        while ((line = csvReader.readLine()) != null) {
            String[] data = line.split(",");
            stations.add(data[0]); // Start station
            stations.add(data[1]); // End station
        }

        GraphNodeAL<String> a = null; // Current station node
        GraphNodeAL<String> b;        // Previous station node
        int l = 0;                    // Index of previous station node

        // Build graph nodes and connect them
        for (int i = 0; i < stations.size(); i++) {
            int k = 0; // Counter to check if node already exists

            // Check if node already exists in the list
            for (int j = 0; j < nodesAl.size(); j++) {
                if (nodesAl.size() == 0) {
                    a = new GraphNodeAL<>(stations.get(0));
                    nodesAl.add(a);
                } else if (stations.get(i).equals(nodesAl.get(j).data)) {
                    k = k + 1;
                    a = nodesAl.get(j);
                    break;
                }
            }

            // If the node doesn't exist, create and add it
            if (k == 0) {
                a = new GraphNodeAL<>(stations.get(i));
                nodesAl.add(a);
            }

            // Connect every second station (i is odd) to the previous one
            if (i % 2 == 1) {
                b = nodesAl.get(l);
                a.connectToNodeUndirected(b); // Undirected connection between nodes
            } else {
                // Store the current index for the next connection
                for (int j = 0; j < nodesAl.size(); j++) {
                    if (a.data.equals(nodesAl.get(j).data)) {
                        l = j;
                        break;
                    }
                }
            }
        }

        // Populate the UI dropdowns with station names
        for (int i = 0; i < nodesAl.size(); i++) {
            startingStop.getItems().add(nodesAl.get(i).data.toString());
            endStop.getItems().add(nodesAl.get(i).data.toString());
            avoidStop.getItems().add(nodesAl.get(i).data.toString());
        }
    }

    // Closes the application when called
    public void exit(ActionEvent event) {
        System.out.println("exit");
        System.exit(-1);
    }

    // Handles logic for user's search choice
    public void choiceBox(ActionEvent actionEvent) {
        int startPoint = -1;
        int endPoint = -1;
        int avoidPoint = -1;
        if (searchOption.getValue().equals("Fewest Stops")) {
            // Placeholder for fewest stops search logic
            for (int i = 0; i < nodesAl.size(); i++) {
                if(startingStop.getValue().equals(nodesAl.get(i).data)){
                    startPoint = i;
                }
                if(endStop.getValue().equals(nodesAl.get(i).data)){
                    endPoint = i;
                }
                if(!avoidStop.getValue().equals("")){
                    if (nodesAl.get(i).data.toString().equals(avoidStop)) {
                        avoidPoint = i;
                    }
                }
            }
//            System.out.println(nodesAl.get(startPoint).data.toString() + " " + nodesAl.get(endPoint).data.toString());
            if(startPoint != -1 && endPoint != -1) {
                ArrayList<GraphNodeAL<?>> results = GraphNodeAL.findPathBreadthFirst((GraphNodeAL<?>) nodesAl.get(startPoint), nodesAl.get(endPoint).data);
                for(int i=0;i<results.size();i++){
                    System.out.println(results.get(i).data);
                    if(i<results.size()-1){System.out.println("vvvv");}
                }
            }
        }
        if (startingStop.getValue().equals("Shortest Route")) {
            // Placeholder for shortest route logic
//            GraphNodeAL.searchGraphDepthFirst(startingStop,endStop);
        }
        if (endStop.getValue().equals("Shortest with fewest changes")) {
            // Placeholder for fewest changes logic
//            GraphNodeAL.(startingStop,endStop);
        }
    }
}