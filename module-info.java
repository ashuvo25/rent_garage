module com.example.rent_garadge {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    requires java.desktop;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
//    requires com.google.auth.oauth2;


    opens com.example.rent_garadge to javafx.fxml;
    exports com.example.rent_garadge;
}