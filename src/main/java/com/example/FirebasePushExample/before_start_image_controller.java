package com.example.FirebasePushExample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class before_start_image_controller {

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Hyperlink prevLink;
    @FXML
    private Hyperlink next_image;

    @FXML
    private Button singUp_link;

    @FXML
    private Circle indicator1, indicator2, indicator3, indicator4;

    @FXML
    private Label titleLabel, descriptionLabel;

    private int currentIndex = 0;
    private ArrayList<String> imageUrls;
    private ArrayList<Circle> indicators;
    private ArrayList<String> titles;
    private ArrayList<String> descriptions;

    @FXML
    public void initialize() {
        imageUrls = new ArrayList<>();
        imageUrls.add(Objects.requireNonNull(getClass().getResource("/com/example/rent_garadge/before/1.png")).toExternalForm());
        imageUrls.add(Objects.requireNonNull(getClass().getResource("/com/example/rent_garadge/before/3.png")).toExternalForm());
        imageUrls.add(Objects.requireNonNull(getClass().getResource("/com/example/rent_garadge/before/1.png")).toExternalForm());
        imageUrls.add(Objects.requireNonNull(getClass().getResource("/com/example/rent_garadge/before/1.png")).toExternalForm());

        indicators = new ArrayList<>();
        indicators.add(indicator1);
        indicators.add(indicator2);
        indicators.add(indicator3);
        indicators.add(indicator4);

        titles = new ArrayList<>();
        titles.add("Premium Vehicle Repairs");
        titles.add("Top-notch Car Services");
        titles.add("Expert Vehicle Maintenance");
        titles.add("Rent Your Garage");

        descriptions = new ArrayList<>();
        descriptions.add("We do the best vehicle modifications and repairs and let you rent out your garage.");
        descriptions.add("Providing exceptional car services to keep your vehicle in top shape.");
        descriptions.add("Reliable vehicle maintenance services from our expert team.");
        descriptions.add("Rent out your garage space with ease and earn extra income.");

        // Set initial background image, title, and description
        updateContent();

        // Set up link actions
        prevLink.setOnAction(event -> showPreviousImage());
        next_image.setOnAction(event -> showNextImage());
        singUp_link.setOnAction(event -> get_start_button());

        // Automatic image switching
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = imageUrls.size() - 1;
        }
        updateContent();
    }
    private void get_start_button() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("become.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) singUp_link.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % imageUrls.size();
        updateContent();
    }

    private void updateContent() {
        backgroundImageView.setImage(new Image(imageUrls.get(currentIndex)));
        titleLabel.setText(titles.get(currentIndex));
        descriptionLabel.setText(descriptions.get(currentIndex));
        updateIndicators();
    }

    private void updateIndicators() {
        for (int i = 0; i < indicators.size(); i++) {
            if (i == currentIndex) {
                indicators.get(i).setFill(Color.WHITE);
            } else {
                indicators.get(i).setFill(Color.GRAY);
            }
        }
    }
}
