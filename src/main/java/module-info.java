module com.example.rent_garadge {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.services.oauth2.v2.rev141;
    requires com.google.api.client;
    requires google.api.client;
    requires java.desktop;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.json.gson;

    opens com.example.rent_garadge to javafx.fxml;
    exports com.example.rent_garadge;
}