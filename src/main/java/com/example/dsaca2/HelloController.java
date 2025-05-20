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

    private ArrayList<GraphNodeAL> nodesAl = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add options to the choiceBox
        searchOption.getItems().addAll("Fewest Stops", "Shortest Route", "Shortest with fewest changes");
        try {
            csvReader();
            detailedMap = new Image(getClass().getResource("/Images/StationMapWithContent.png").openStream());
            mapView.setImage(detailedMap);
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
        ArrayList<String> stations = new ArrayList<>();


        while ((line = csvReader.readLine()) != null) {
//            System.out.println(line);
            String[] data = line.split(",");
//            System.out.println("Start = " + data[0] + " End = " + data[1]);

            stations.add(data[0]);
            stations.add(data[1]);



        }

        GraphNodeAL<String> a=null;
        GraphNodeAL<String> b;

        int l=0;

        for (int i = 0; i < stations.size(); i++) {
            int k=0;
            for (int j = 0; j < nodesAl.size(); j++) {
                if(nodesAl.size()==0){
                    a=new GraphNodeAL<>(stations.get(0));
                    nodesAl.add(a);
                } else if (stations.get(i).equals(nodesAl.get(j).data)){
                    k=k+1;
                    a=nodesAl.get(j);
                    break;
                }
            }


            if(k==0){
                a = new GraphNodeAL<>(stations.get(i));
                nodesAl.add(a);
            }

            if(i%2==1){
                b=nodesAl.get(l);
                a.connectToNodeUndirected(b);
            }else{
                for (int j=0;j<nodesAl.size();j++){
                    if(a.data.equals(nodesAl.get(j).data)){
                        l=j;
                        break;
                    }
                }
            }
        }

//        for (int i=0;i < nodesAl.size();i++){
//            System.out.println(nodesAl.get(i).data);
//            for (int j=0;j < nodesAl.get(i).adjList.size();j++) {
//                GraphNodeAL<String> temp= (GraphNodeAL<String>) nodesAl.get(i).adjList.get(j);
//                System.out.println(temp.data);
//            }
//            System.out.println("\n");
//        }
        for (int i=0;i<nodesAl.size();i++){
            startingStop.getItems().add( nodesAl.get(i).data.toString());
            endStop.getItems().add( nodesAl.get(i).data.toString());
            avoidStop.getItems().add(nodesAl.get(i).data.toString());
        }
    }

    public void exit(ActionEvent event)
    {
        System.out.println("exit");
        System.exit(-1);
    }

    public void choiceBox(ActionEvent actionEvent) {
        if (searchOption.getValue().equals("Fewest Stops")) {
            //have to make method that takes choice box inputs and runs them through GraphNodeAL.traverseDepthFirstSearch()
            GraphNodeAL.searchGraphDepthFirst(startingStop,endStop);// something like this
        }//same thing for the rest
        if (startingStop.getValue().equals("Shortest Route")) {
//            GraphNodeAL.searchGraphDepthFirst(startingStop,endStop);
        }
        if (endStop.getValue().equals("Shortest with fewest changes")) {
//            GraphNodeAL.(startingStop,endStop);
        }
    }
}