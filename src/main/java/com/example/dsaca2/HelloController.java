package com.example.dsaca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

import static com.example.dsaca2.GraphNode.findAllPathsDepthFirst;
import static com.example.dsaca2.GraphNode.findPathBreadthFirst;

public class HelloController implements Initializable {

    // FXML-injected UI components
    @FXML
    private ChoiceBox<String> searchOption;
    @FXML
    public ChoiceBox<String> startingStop;
    @FXML
    public ChoiceBox<String> endStop;
    @FXML
    public ChoiceBox<String> avoidStop;
    @FXML
   public ImageView mapView;
    @FXML
    private Pane paneView;

    // Images for map display
    private Image detailedMap;
    private Image simpleMap;

    // List to hold graph nodes representing stations
    private ArrayList<GraphNode> nodesAl = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the search option choice box
        searchOption.getItems().addAll("Fewest Stops","Fewest Stops Multiple Routes", "Shortest Route", "Shortest with fewest changes");
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

    // Reads station data from a CSV file and builds a graph
    public void csvReader() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/vienna_subway.csv");
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        ArrayList<String> stations = new ArrayList<>();
        int counter = 0;
        // Read each line from the CSV and extract station pairs
        while ((line = csvReader.readLine()) != null) {
            String[] data = line.split(",");
//            stations.add(data[0]); // Start station
//            stations.add(data[1]); // End station

            GraphNode<String> a = null; // Current station node
            GraphNode<String> b = null; // Previous station node

            if (nodesAl.size() == 0) {
                a = new GraphNode<>(data[0]);
                b = new GraphNode<>(data[1]);
                nodesAl.add(a);
                nodesAl.add(b);
            } else {
                for (int i = 0; i < nodesAl.size(); i++) {
                    if (data[0].equals(nodesAl.get(i).data)) {
                        a = nodesAl.get(i);
                    }
                    if (data[1].equals(nodesAl.get(i).data)) {
                        b = nodesAl.get(i);
                    }
                }
                if (a == null) {
                    a = new GraphNode<>(data[0]);
                    nodesAl.add(a);
                }
                if (b == null) {
                    b = new GraphNode<>(data[1]);
                    nodesAl.add(b);
                }
            }
            a.connectToNodeUndirected(b, Integer.parseInt(data[4]));
        }
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
    public void choiceBox(ActionEvent actionEvent) throws IOException {
        int startPoint = -1;
        int endPoint = -1;
        int avoidPoint = -1;
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
        if (searchOption.getValue().equals("Fewest Stops")) {
            // Placeholder for fewest stops search logic
            if(startPoint != -1 && endPoint != -1) {
                ArrayList<GraphNode<?>> results = findPathBreadthFirst(nodesAl.get(startPoint), nodesAl.get(endPoint).data);
                System.out.println("-------------------------------");
                for(int i=0;i<results.size();i++){
                    System.out.println(results.get(i).data);
                    if(i<results.size()-1){
                        System.out.println("    |    ");
                        System.out.println("    V    ");
                    }
                }
                showRouteNoCost(results);
            }
        }
        if (searchOption.getValue().equals("Fewest Stops Multiple Routes")) {
            ArrayList<ArrayList<GraphNode<?>>> results = findAllPathsDepthFirst((GraphNode<?>) nodesAl.get(startPoint), null, nodesAl.get(endPoint).data);
            for(int i=0;i<results.size();i++){
                System.out.println("-------------------------------");
                for(int j=0;j<results.size();j++) {
                    System.out.println(results.get(i).get(j).data);
                    if (i < results.size()-1) {
                        System.out.println("    |    ");
                        System.out.println("    V    ");
                    }
                }
            }
        }
        if (startingStop.getValue().equals("Shortest Route")) {
        }
        if (endStop.getValue().equals("Shortest with fewest changes")) {

        }
    }

    public void showRouteNoCost(ArrayList<GraphNode<?>> arrayList) throws IOException {
        clearScreen();

    }

    public void clearScreen() throws IOException {
        for(Object i : paneView.getChildren()){
            if (!i.getClass().equals(ImageView.class)){
                paneView.getChildren().remove(i);
            }
        }
    }
}