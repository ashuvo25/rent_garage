module com.example.rent_garadge {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.rent_garadge to javafx.fxml;
    exports com.example.rent_garadge;
}