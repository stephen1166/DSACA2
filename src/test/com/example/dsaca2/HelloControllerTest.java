package com.example.dsaca2;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class HelloControllerTest {

    private HelloController controller;

    @BeforeAll
    static void initJfx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() {
        controller = new HelloController();

        controller.startingStop = new ChoiceBox<>();
        controller.endStop = new ChoiceBox<>();
        controller.avoidStop = new ChoiceBox<>();
    }

    @Test
    void testCsvReaderLoadsStations() throws Exception {
        controller.csvReader();

        List<String> startingItems = controller.startingStop.getItems();
        List<String> endItems = controller.endStop.getItems();
        List<String> avoidItems = controller.avoidStop.getItems();

        assertFalse(startingItems.isEmpty(), "Starting stop list empty");
        assertEquals(startingItems, endItems, "End stops should match starting stops");
        assertEquals(startingItems, avoidItems, "Avoid stops should match starting stops");

        // Optional: known station check
        assertTrue(
                startingItems.contains("Karlsplatz") || startingItems.contains("Stephansplatz"),
                "station not in the list"
        );
    }

    @Test
    void testDetailedMapSetsImage() throws Exception {
        controller.mapView = new ImageView(); // use real one
        controller.detailedMap(null);
        assertNotNull(controller.mapView.getImage(), "ImageView, no image");
    }

    @Test
    void testSimpleMapSetsImage() throws Exception {
        controller.mapView = new ImageView(); // use real one
        controller.simpleMap(null);
        assertNotNull(controller.mapView.getImage(), "ImageView, no image");
    }

}
