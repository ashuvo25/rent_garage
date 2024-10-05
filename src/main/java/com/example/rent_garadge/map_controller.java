package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jdk.swing.interop.SwingInterOpUtils;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class map_controller {

    // Global variables to store latitude and longitude
    private String Latitude = "";
    private String Longitude = "";

    @FXML
    private WebView mapView;

    @FXML
    private Button pin_loction;

    Map<String, Object> details;

    @FXML
    public void initialize() {

    }


    public void garageDetails(Map<String, Object> details) {
        this.details=details;
        String location=(String) details.get("city")+","+(String)details.get("address");
//        System.out.println("This is nothing");
//        System.out.println(details);
        WebEngine webEngine = mapView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        String mapHTML = generateMapHTML(location);
        webEngine.loadContent(mapHTML);

        // Get the JavaScript window object to expose Java methods to it
        webEngine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("app", this);  // Expose the 'app' object (this Java class) to JavaScript
            }
        });
    }

    // Method to be called from JavaScript to update latitude and longitude
    public void updateLocation(String lat, String lon) {
        this.Latitude = lat;
        this.Longitude = lon;
        details.put("Latitude", this.Latitude );
        details.put("Longitude", this.Longitude);

        // Assuming garagedetails contains an integer "slot"
        int num = Integer.parseInt(details.get("slot")+""); // Extract the number of slots
        // Initialize the slotNum map
        Map<String, Object> slotNum = new HashMap<>();
        // Iterate over the number and add dynamic keys
        for (int i = 1; i <= num; i++) {
            String key = "slot" + i;  // Create keys like slot1, slot2, slot3, etc.
            slotNum.put(key, true);    // Add to the map with value true
        }
        String value=FirebaseConfig.datainput("garage_slot",RentGaradge.user_id,slotNum);
        System.out.println(value);
        RentGaradge.garageDetails=details;

        System.out.println(details);

        openNextWindow();
    }

    private void openNextWindow() {
        try {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Prof_image_take.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

//            // Get the controller after loading the FXML
//            LoginPanelUserControl controller = fxmlLoader.getController();
//
//            // Pass the selections to the new controller
//            controller.garageDetails(details);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

//            // Close the current stage
//            Stage currentStage = (Stage) ((Node) someNode).getScene().getWindow(); // someNode should be a reference to any node in the current scene
//            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateMapHTML(String locationName) {
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
                        }
                        #map {
                            width: 425px;
                            height: 350px;
                            position: relative;
                        }
                        .dot {
                            position: absolute;
                            width: 10px;
                            height: 10px;
                            background-color: red;
                            border-radius: 50%;
                            top: 50%;
                            left: 50%;
                            transform: translate(-50%, -50%);
                        }
                        #locationInfo {
                            margin-top: 10px;
                            font-weight: bold;
                        }
                    </style>
                </head>
                <body>
                    <div id="map"></div>
                    <div class="dot" id="dot"></div>
                    <button id="pinButton">Pin Your Location</button>
                    <div id="locationInfo"></div>

                    <script>
                        var map;
                        var dotLatitude, dotLongitude;

                        function loadMapScenario() {
                            map = new Microsoft.Maps.Map(document.getElementById('map'), {
                                credentials: 'AkcKTkaCZKKAdrUrSATJWbV7xVleTJ1HtvHxp04_PKIVO1w5SSJGokoMWimJITcj',
                                zoom: 10,
                                center: new Microsoft.Maps.Location(0, 0)
                            });

                            Microsoft.Maps.loadModule('Microsoft.Maps.Search', function () {
                                var searchManager = new Microsoft.Maps.Search.SearchManager(map);
                                var searchRequest = {
                                    where: '""" + locationName + """
                                    ',
                                    callback: function (result) {
                                        if (result && result.results && result.results.length > 0) {
                                            var location = result.results[0].location;
                                            map.setView({
                                                center: location,
                                                zoom: 17
                                            });
                                            updateDotPosition();
                                        }
                                    },
                                    errorCallback: function (e) {
                                        console.log('Error:', e);
                                    }
                                };
                                searchManager.geocode(searchRequest);
                            });

                            Microsoft.Maps.Events.addHandler(map, 'viewchangeend', updateDotPosition);
                        }

                        function updateDotPosition() {
                            var center = map.getCenter();
                            dotLatitude = center.latitude;
                            dotLongitude = center.longitude;
                        }

                        document.getElementById('pinButton').addEventListener('click', function() {
                            if (dotLatitude && dotLongitude) {
//                                document.getElementById('locationInfo').innerText =
//                                    `Latitude: ${dotLatitude.toFixed(6)}, Longitude: ${dotLongitude.toFixed(6)}`;

                                // Call the Java method to update the global variables
                                window.app.updateLocation(dotLatitude.toFixed(6), dotLongitude.toFixed(6));
                            } else {
                                document.getElementById('locationInfo').innerText = 'Location not pinned yet.';
                            }
                        });
                    </script>
                </body>
                </html>
                """;
    }


}
