package com.example.rent_garadge;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentPageController {
    @FXML
    private Button bike_buttons;

    @FXML
    private Button car_buttons;

    @FXML
    private Button home_btn;

    @FXML
    private Button home_btn4;

    @FXML
    private Pane nav_bar;

    @FXML
    private Button updown_maps;

    @FXML
    private WebView map_view;

    @FXML
    private Button dropdown_map;
    @FXML
    private Pane rent_background;

    private boolean isRentBackgroundVisible = false;

    @FXML
    private void initialize() {
        bike_buttons.setOnAction(event -> handleBikeButtonClick());
        car_buttons.setOnAction(event -> handleCarButtonClick());
        home_btn4.setOnAction(event -> profileFunct());
        home_btn.setOnAction(event -> homescreen());
        rent_background.setLayoutY(420);
        map_view.setVisible(true);
        dropdown_map.setVisible(false);
        updown_maps.setVisible(true);

        List<Map<String, Object>> getAllGarageDetails = FirebaseConfig.getAllGarageDetails();
        System.out.println(getAllGarageDetails);

        // Generate and load the map with markers
        WebEngine webEngine = map_view.getEngine();
        webEngine.setJavaScriptEnabled(true);
        String mapHTML = generateMapHTML("Dhaka", getAllGarageDetails);
        webEngine.loadContent(mapHTML);

        // Expose Java methods to JavaScript
        webEngine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("app", this);  // Expose the 'app' object (this Java class) to JavaScript
            }
        });
    }

    @FXML
    private void handleCarButtonClick() {
        car_buttons.setStyle("-fx-background-color: #5c5cba;");
        bike_buttons.setStyle("-fx-background-color: #fdfeff;");
    }

    @FXML
    private void handleBikeButtonClick() {
        bike_buttons.setStyle("-fx-background-color: #5c5cba;");
        car_buttons.setStyle("-fx-background-color: #fdfeff;");
    }

    private String generateMapHTML(String locationName, List<Map<String, Object>> locations) {
        // Create a new list to store the formatted locations
        List<Map<String, Double>> formattedLocations = new ArrayList<>();

        for (Map<String, Object> detail : locations) {
            // Retrieve latitude and longitude as String
            String latitudeString = (String) detail.get("Latitude");
            String longitudeString = (String) detail.get("Longitude");

            // Convert String values to double
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);

            // Create a new map for the location and add it to the formatted list
            Map<String, Double> location = new HashMap<>();
            location.put("lat", latitude);
            location.put("lng", longitude);
            formattedLocations.add(location);
        }
        System.out.println(formattedLocations);

        return """
                <!DOCTYPE html>
                       <html lang="en">
                       <head>
                           <meta charset="UTF-8">
                           <meta name="viewport" content="width=device-width, initial-scale=1.0">
                           <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?key=AkcKTkaCZKKAdrUrSATJWbV7xVleTJ1HtvHxp04_PKIVO1w5SSJGokoMWimJITcj&callback=loadMapScenario' async defer></script>
                           <title>Map View</title>
                           <style>
                               body {
                                   margin: 0;
                                   padding: 0;
                                   display: flex;
                                   flex-direction: column;
                                   justify-content: center;
                                   align-items: center;
                                   height: 100vh;
                                   background-color: #f0f0f0;
                               }
                               #map {
                                   width: 340px;
                                   height: 335px;
                                   position: relative;
                               }
                               #cityName {
                                   position: absolute;
                                   top: 50%;
                                   left: 50%;
                                   transform: translate(-50%, -50%);
                                   color: white;
                                   font-size: 24px;
                                   font-weight: bold;
                                   text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.7);
                                   pointer-events: none; /* Prevent interaction with the text */
                               }
                           </style>
                       </head>
                       <body>
                           <div id="map">
                               <div id="cityName">Dhaka, Gulshan 1</div>
                           </div>
                           <div id="locationInfo"></div>
                       
                           <script>
                               var map;
                               var locations = [
                                   { lat: 23.8103, lng: 90.4125 }, // Dhaka
                                   { lat: 23.7384, lng: 90.3880 }, // Gulshan
                                   { lat: 23.8352, lng: 90.4223 }, // Dhanmondi
                                   { lat: 23.7542, lng: 90.3932 }, // Mirpur
                                   { lat: 23.8457, lng: 90.4140 }, // Uttara
                                   { lat: 23.8704, lng: 90.3910 }, // Tejgaon
                                   { lat: 23.8739, lng: 90.4138 }, // Mohammadpur
                                   { lat: 23.7545, lng: 90.3898 }, // Bashundhara
                                   { lat: 23.8018, lng: 90.3664 }, // Lalbagh
                                   { lat: 23.8705, lng: 90.3553 }  // Sabujbagh
                               ];
                       
                               function loadMapScenario() {
                                   map = new Microsoft.Maps.Map(document.getElementById('map'), {
                                       credentials: 'AkcKTkaCZKKAdrUrSATJWbV7xVleTJ1HtvHxp04_PKIVO1w5SSJGokoMWimJITcj',
                                       zoom: 11,
                                       center: new Microsoft.Maps.Location(23.8103, 90.4125) // Center on Dhaka
                                   });
                       
                                   // Create a black pin for Dhaka
                                   var dhakaLocation = new Microsoft.Maps.Location(23.8103, 90.4125);
                                   var dhakaPin = new Microsoft.Maps.Pushpin(dhakaLocation, {
                                       color: 'black'
                                   });
                                   map.entities.push(dhakaPin);
                       
                                   // Loop through the other locations array to create pins
                                   locations.forEach(function(location) {
                                       var loc = new Microsoft.Maps.Location(location.lat, location.lng);
                                       var pin = new Microsoft.Maps.Pushpin(loc, {
                                           color: 'red'
                                       });
                                       map.entities.push(pin);
                                   });
                       
                                   // Set map view to encompass all pins
                                   var bounds = Microsoft.Maps.LocationRect.fromLocations(locations.map(function(location) {
                                       return new Microsoft.Maps.Location(location.lat, location.lng);
                                   }));
                                   bounds = bounds.combine(Microsoft.Maps.LocationRect.fromLocations([dhakaLocation])); // Include Dhaka in the bounds
                                   map.setView({ bounds: bounds });
                               }
                           </script>
                       </body>
                       </html>
                       
        """;
    }


    private void profileFunct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profilepage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn4.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void homescreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdownMaps() {
        if (!isRentBackgroundVisible) {
            map_view.setVisible(false);
            TranslateTransition transitionUp = new TranslateTransition(Duration.millis(500), rent_background);
            transitionUp.setToY(-340);
            transitionUp.play();

            updown_maps.setVisible(false);
            dropdown_map.setVisible(true);

            isRentBackgroundVisible = true;
        }
    }

    @FXML
    private void handleDropdownMap() {
        if (isRentBackgroundVisible) {
            TranslateTransition transitionDown = new TranslateTransition(Duration.millis(500), rent_background);
            transitionDown.setToY(-30);
            transitionDown.play();
            transitionDown.setOnFinished(e -> map_view.setVisible(true));
            dropdown_map.setVisible(false);
            updown_maps.setVisible(true);

            isRentBackgroundVisible = false;
        }
    }

    // Method to be called from JavaScript
    public void updateLocation(String latitude, String longitude) {
        System.out.println("Pinned Location - Latitude: " + latitude + ", Longitude: " + longitude);
        // You can add any additional logic to handle the pinned location here
    }
}
