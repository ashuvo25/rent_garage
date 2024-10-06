package com.example.rent_garadge;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HistoryController {

    @FXML
    private Button back_home;

    @FXML
    private Button comments;

    @FXML
    private Pane history_pane;

    @FXML
    private Pane history_pane1;

    @FXML
    private Pane history_pane2;

    @FXML
    private Button profileHistory;

    @FXML
    private Button profile_view;

    @FXML
    private Text serial_1;

    @FXML
    private Text serial_2;

    @FXML
    private Text serial_3;

    @FXML
    private Text address_1;
    @FXML
    private Text address_2;
    @FXML
    private Text address_3;

    @FXML
    private Text date_1;
    @FXML
    private Text date_2;
    @FXML
    private Text date_3;



    List<Map<String, Object>> history;

    private void profileFunct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profilepage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) profile_view.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
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


    @FXML
    public void initialize() {
        history_pane.setVisible(false);
        history_pane1.setVisible(false);
        history_pane2.setVisible(false);

        profile_view.setOnAction(event -> profileFunct());
        back_home.setOnAction(event -> backTohome());

        // Assuming history is a list of maps (List<Map<String, Object>>)
        List<Map<String, Object>> history = FirebaseConfig.getAllGarageDetails("rent_list");
        System.out.println(history);

        // Check if history is not null and has elements
        if (history != null && !history.isEmpty()) {
            // Filter the list to only include entries where the renter matches RentGaradge.user_id
            List<Map<String, Object>> filteredHistory = new ArrayList<>();

            for (Map<String, Object> rent : history) {
                // Checking if the renter matches the user_id (convert to String to handle null cases)
                if (RentGaradge.user_id.equals(String.valueOf(rent.get("renter")))) {
                    filteredHistory.add(rent);
                }
            }

            // If filtered history is empty, show a message and exit
            if (filteredHistory.isEmpty()) {
                System.out.println("No matching rent history found for user.");
                return;
            }

            // Assuming start_time is stored in format like "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Sort the filtered history based on start_time in descending order
            filteredHistory.sort((rent1, rent2) -> {
                try {
                    // Check if both rents have start_time before parsing
                    if (rent1.containsKey("start_time") && rent2.containsKey("start_time")) {
                        Date date1 = sdf.parse((String) rent1.get("start_time"));
                        Date date2 = sdf.parse((String) rent2.get("start_time"));
                        return date2.compareTo(date1);  // Sort in descending order
                    } else {
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            });

            // Print the most recent 3 entries (or fewer if there are less than 3)
            System.out.println("Most recent 3 rents for user:");
            for (int i = 0; i < Math.min(3, filteredHistory.size()); i++) {
                Map<String, Object> rent = filteredHistory.get(i);
                System.out.println(rent);
                String start_time = rent.get("start_time")+"";
                String date_part = start_time.split(" ")[0];

               if(i==0){
                   history_pane.setVisible(true);
                   serial_1.setText("Parking 1");
                   address_1.setText(rent.get("address")+","+rent.get("city"));
                   date_1.setText("Date:"+date_part);

               } else if (i==1) {
                   history_pane1.setVisible(true);
                   serial_2.setText("Parking 2");
                   address_2.setText(rent.get("address")+","+rent.get("city"));
                   date_2.setText("Date:"+date_part);
               } else if (i==2) {
                   history_pane2.setVisible(true);
                   serial_3.setText("Parking 3");
                   address_3.setText(rent.get("address")+","+rent.get("city"));
                   date_2.setText("Date:"+date_part);

               }
//               serial_3.

            }
        } else {
            System.out.println("No rent history found.");
        }
    }

}

