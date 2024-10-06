package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class prof_image_takeController {

    @FXML
    private Button add_image;

    @FXML
    private Button choose_photo;

    @FXML
    private Button dropdown_mutton;

    @FXML
    private Pane popup_pane;

    @FXML
    private Button prof_img_next;

    @FXML
    private Button take_photo;

    @FXML
    private TextField phone_number;


    private boolean isPopupVisible = false;
    private File selectedImageFile;
//    private Mat capturedImage;

    @FXML
    public void initialize() {
        popup_pane.setLayoutY(498);
        prof_img_next.setVisible(true);
        String num=phone_number.getText();

        RentGaradge.garageDetails.put("owner_number",num);
        choose_photo.setOnAction(event -> handleChoosePhoto());
        prof_img_next.setOnAction(event -> openNextWindow());


    }

    @FXML
    private void handleAddImageButton() {
        if (!isPopupVisible) {
            popup_pane.setLayoutY(328);
            prof_img_next.setVisible(false);
            isPopupVisible = true;
        }
    }

    @FXML
    private void handleDropdownMutton() {
        if (isPopupVisible) {
            popup_pane.setLayoutY(498);
            isPopupVisible = false;
            prof_img_next.setVisible(true);
        }
    }

    @FXML
    private void handleChoosePhoto() {
        // Open FileChooser to select an image from the computer
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Profile Photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) choose_photo.getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);

        if (selectedImageFile != null) {
            // You now have the selected file
            String path="" + selectedImageFile.getAbsolutePath();
            RentGaradge.garageDetails.put("image_url",path);
            System.out.println(RentGaradge.garageDetails);
        }
    }

    @FXML
    private void handleTakePhoto() {
        // Initialize the camera (requires OpenCV library)
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME); // Load the OpenCV native library

        VideoCapture camera = new VideoCapture(0); // Open the default camera (0)

        if (!camera.isOpened()) {
            System.out.println("Error: Camera not accessible");
            return;
        }

        Mat frame = new Mat();
        if (camera.read(frame)) {
            // Store the captured image in the `capturedImage` variable
            Mat capturedImage = frame;
            Imgcodecs.imwrite("captured_image.jpg", capturedImage); // Optionally save the image
            System.out.println("Image captured successfully.");
        }

        camera.release(); // Release the camera
    }


    private void openNextWindow() {
        try {
            // Load the FXML file and create a new scene
            String re= FirebaseConfig.datainput("garage_details",RentGaradge.user_id,RentGaradge.garageDetails);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) prof_img_next.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
