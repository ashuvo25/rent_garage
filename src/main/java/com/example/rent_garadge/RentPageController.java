package com.example.rent_garadge;

import java.util.*;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

class LocationInfo{
    private  String email;
    private double longi;
    private double lati;

    public LocationInfo(String email, double longi, double lati) {
        this.email = email;
        this.longi = longi;
        this.lati = lati;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "email='" + email + '\'' +
                ", longi=" + longi +
                ", lati=" + lati +
                '}';
    }
}

public class RentPageController {
    @FXML
    private ScrollPane scrollPaneForRentPage;

    @FXML
    private VBox infoContainer;

    @FXML
    private Text garage_address;
    @FXML
    private Text distance_id;

    @FXML
    private TextField search;

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
    private Button back_home;
    @FXML
    private Pane rent_background;
    private List<Map<String, Object>> getAllGarageDetails;
    private boolean isRentBackgroundVisible = false;

    private void populateGarageList(List<Map<String, Object>> garageDetailsList) {
        for (Map<String, Object> garageDetails : garageDetailsList) {
            System.out.println(garageDetails.get("address"));

            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("list.fxml"));
                HBox hBox = fxmlLoader.load();
                ListController listController = fxmlLoader.getController();
                listController.setGarageDetails(garageDetails);
                infoContainer.getChildren().add(hBox);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }

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
        back_home.setOnAction(event -> backTohome());

        getAllGarageDetails = FirebaseConfig.getAllGarageDetails();
        System.out.println(getAllGarageDetails);
        System.out.println(getAllGarageDetails.get(0).getClass());

        // Generate and load the map with markers
        WebEngine webEngine = map_view.getEngine();
        webEngine.setJavaScriptEnabled(true);
        String mapHTML = generateMapHTML("Gulshan 2", getAllGarageDetails);
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

    public void updateLocation(String distanceInfo) {
        // Assuming you want to display the distance info to the user
//        System.out.println(distanceInfo + "");
        String loc_info[] = distanceInfo.split(",,,");

        for(String info: loc_info){
            String email = info.split(",")[1];
            double dist = Double.parseDouble(info.split(",")[0]);

            for(Map<String, Object> garage: getAllGarageDetails){
                if (email.equals(garage.get("email"))){
                    garage.put("distance", dist);
                }
            }
        }

        System.out.println(getAllGarageDetails);
        System.out.println("After sort\n");
        try{
            getAllGarageDetails.sort((g1, g2) -> {
                Double distance1 = (Double) g1.get("distance");
                Double distance2 = (Double) g2.get("distance");
                return distance1.compareTo(distance2);
            });
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        populateGarageList(getAllGarageDetails);



    }


        private String generateMapHTML(String locationName, List<Map<String, Object>> locations) {
        // Create a new list to store the formatted locations
        ArrayList<LocationInfo> formattedLocations = new ArrayList<>();

        for (Map<String, Object> detail : locations) {
            // Retrieve latitude and longitude as String
            String latitudeString = (String) detail.get("Latitude");
            String longitudeString = (String) detail.get("Longitude");
            String email=(String) detail.get("email");

            // Convert String values to double
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);

            // Create a new map for the location and add it to the formatted list
//            Map<String, String> location = new HashMap<>();
//            location.put("lat", latitudeString);
//            location.put("lng", longitudeString);
//            location.put("email",email);
            formattedLocations.add(new LocationInfo(email , longitude, latitude));
        }

        // Convert the formattedLocations list to a JSON-like JavaScript array format
        StringBuilder locationArrayJS = new StringBuilder("[");
        for (LocationInfo info : formattedLocations) {
            locationArrayJS.append("[").append(info.getLati()).append(", ").append(info.getLongi()).append(", '").append(info.getEmail()).append("'], ");
        }
        // Remove the trailing comma and space, then close the array
        if (locationArrayJS.length() > 1) {
            locationArrayJS.setLength(locationArrayJS.length() - 2); // Trim the last ", "
        }
        locationArrayJS.append("]");
            System.out.println(locationArrayJS);
        // HTML and JavaScript template with dynamic locations from Java
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
                        white-space: pre-wrap;
                    }
                </style>
            </head>
            <body>
                <div id="map"></div>
                <div class="dot" id="dot"></div> <!-- Center dot -->
                <button id="pinButton">Pin Your Location</button>
                <div id="locationInfo"></div>

                <script>
                    var map;
                    var centerLatitude, centerLongitude;

                    // Coordinates array passed from Java (formattedLocations)
                    var coordinatesArray = """ + locationArrayJS.toString() + """
                    ;

                    function loadMapScenario() {
                        map = new Microsoft.Maps.Map(document.getElementById('map'), {
                            credentials: 'AkcKTkaCZKKAdrUrSATJWbV7xVleTJ1HtvHxp04_PKIVO1w5SSJGokoMWimJITcj',
                            zoom: 10,
                            center: new Microsoft.Maps.Location(0, 0)
                        });

                        // Add pins (dots) for all locations in the array
                        coordinatesArray.forEach((item, i) => {
                             let latitude = item[0];
                             let longitude = item[1];
                             let email = item[2];
                             
                             var location = new Microsoft.Maps.Location(latitude, longitude);
                             var pin = new Microsoft.Maps.Pushpin(location, {
                               title: 'Point ' + (i + 1),
                                color: 'red'
                             });
                             
                             map.entities.push(pin);
                         });
                        
//                        for (var i = 0; i < coordinatesArray.length; i++) {
//                            var location = new Microsoft.Maps.Location(coordinatesArray[i][0], coordinatesArray[i][1]);
//                            var pin = new Microsoft.Maps.Pushpin(location, {
//                                title: 'Point ' + (i + 1),
//                                color: 'red'
//                            });
//                            map.entities.push(pin); // Add the pin to the map
//                        }

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
                                        updateCenterDotPosition(); // Update the center dot position after search
                                    }
                                },
                                errorCallback: function (e) {
                                    console.log('Error:', e);
                                }
                            };
                            searchManager.geocode(searchRequest);
                        });

                        Microsoft.Maps.Events.addHandler(map, 'viewchangeend', updateCenterDotPosition);
                    }

                    function updateCenterDotPosition() {
                        var center = map.getCenter();
                        centerLatitude = center.latitude;
                        centerLongitude = center.longitude;
                    }

                    // Haversine distance formula to calculate the distance between two sets of lat/long coordinates
                    function haversineDistance(lat1, lon1, lat2, lon2) {
                        var R = 6371; // Radius of the Earth in km
                        var dLat = (lat2 - lat1) * Math.PI / 180;
                        var dLon = (lon2 - lon1) * Math.PI / 180;
                        var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
                        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                        var distance = R * c; // Distance in km
                        return distance;
                    }

                    document.getElementById('pinButton').addEventListener('click', function() {
                                              if (centerLatitude !== undefined && centerLongitude !== undefined) {
                                                  // Create an array to store distances and coordinate info
                                                  var distanceInfoArray = [];
                                                  for (var i = 0; i < coordinatesArray.length; i++) {
                                                      var lat = coordinatesArray[i][0];
                                                      var lon = coordinatesArray[i][1];
                                                      var email = coordinatesArray[i][2];
                                                      var distance = haversineDistance(centerLatitude, centerLongitude, lat, lon);
                                                     
                                                      // Push distance and corresponding coordinate info into an array
                                                      distanceInfoArray.push({
                                                          lat: lat,
                                                          lon: lon,
                                                          email: email,
                                                          distance: distance
                                                      });
                                                  }
                                                 
                                                  // Sort the array by distance
                                                  distanceInfoArray.sort(function(a, b) {
                                                      return a.distance - b.distance;
                                                  });
                                                 
                                                  // Generate the output string with sorted distances
                                                  let distanceInfo = '';
                                                  for (var i = 0; i < distanceInfoArray.length; i++) {
                                                      distanceInfo += `${distanceInfoArray[i].distance.toFixed(2)},${distanceInfoArray[i].email},,,`;
                                                  }
                                                 
                                                  // Send the sorted information to the Java method
                                                  window.app.updateLocation(distanceInfo);
                                                  document.getElementById('locationInfo').style.display = "none";
                                              } else {
                                                  document.getElementById('locationInfo').innerText = 'Center coordinates are not available yet.';
                                              }
                                          });
                                          
                               
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

    private  void  homescreen(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
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
    private void backTohome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) back_home.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
