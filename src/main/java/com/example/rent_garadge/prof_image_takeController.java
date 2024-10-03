package com.example.rent_garadge;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
         private boolean isPopupVisible = false;
    @FXML
    public void initialize() {

        popup_pane.setLayoutY(498);
        prof_img_next.setVisible(true);
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

}
