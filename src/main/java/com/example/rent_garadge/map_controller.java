package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class map_controller {

    @FXML
    private WebView mapView;

    public void initialize() {
        // Load the Bing Maps HTML content into the WebView when the controller is initialized
        loadMap();
    }

    private void loadMap() {
        WebEngine webEngine = mapView.getEngine();
        String htmlContent = generateBingMapHTML();
        webEngine.loadContent(htmlContent);
    }

    private String generateBingMapHTML() {
        String apiKey = "AkcKTkaCZKKAdrUrSATJWbV7xVleTJ1HtvHxp04_PKIVO1w5SSJGokoMWimJITcj";  // Your Bing Maps API Key
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <script type='text/javascript' src='https://www.bing.com/api/maps/mapcontrol?key=" + apiKey + "&callback=loadMapScenario' async defer></script>\n" +
                "    <title>Map of Dhaka</title>\n" +
                "    <style>\n" +
                "        #map {\n" +
                "            width: 100%;\n" +
                "            height: 90vh;\n" +  // Make sure the map takes up most of the screen height
                "            background-color: #e7fff9;\n" +  // Ensure background is not white
                "        }\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #032030;\n" +  // Ensure a different background color for visibility
                "            color: white;\n" +  // Text color to ensure it stands out
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        .info {\n" +
                "            padding: 10px;\n" +
                "            background-color: rgba(0, 0, 0, 0.7);\n" +  // Make background slightly transparent
                "            color: white;\n" +
                "            position: absolute;\n" +
                "            bottom: 10px;\n" +
                "            left: 10px;\n" +
                "            z-index: 1000;\n" +  // Ensure the info box is above the map
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"map\"></div>\n" +
                "    <div class='info'>Location: Dhaka, Bangladesh</div>\n" +  // Add an info box for location
                "    <script>\n" +
                "        function loadMapScenario() {\n" +
                "            var map = new Microsoft.Maps.Map(document.getElementById('map'), {\n" +
                "                credentials: '" + apiKey + "',\n" +
                "                center: new Microsoft.Maps.Location(23.8103, 90.4125),\n" +  // Dhaka's coordinates
                "                zoom: 12\n" +
                "            });\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }

}
