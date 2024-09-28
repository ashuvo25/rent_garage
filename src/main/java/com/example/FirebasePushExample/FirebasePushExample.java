//package com.example.rent_garadge;
package com.example.FirebasePushExample;
//import com.example.rent_garadge.RentGaradge;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.auth.oauth2.GoogleCredentials;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Application.launch;

public class FirebasePushExample extends Application {
    public static Firestore db;

    public void start(Stage stage) throws IOException {
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/rent_garadge/logo.png")));
//
//        stage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(FirebasePushExample.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 500);


        stage.setTitle("RENT GARAGE!");
        stage.setScene(scene);
        stage.show();
    }

    public static void firestore_connection() {
        try {
            // Initialize Firebase with the service account
            FileInputStream serviceAccount = new FileInputStream("garage-6f5fe-firebase-adminsdk-v6dcj-bae29d0410.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();



            // Initialize the Firebase app only once
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
//                System.out.println("Connected to Firestore123");
            }

            // Get Firestore instance
            db = FirestoreClient.getFirestore();
            System.out.println("Connected to Firestore");



        } catch (IOException e) {
            System.out.println("Error initializing Firebase: " + e.getMessage());
        }
    }

    public static boolean person(String collection, String document, Map<String, Object> data) {
        firestore_connection();
        try {
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<WriteResult> result = docRef.set(data);
//            System.out.println(db);
//            System.out.println(document);
            // Wait for the result and check the status
            WriteResult writeResult = result.get();
            System.out.println("Data stored at: " + writeResult.getUpdateTime());
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {

        int id = (int) (Math.random() * 10000);
        Map<String, Object> user = new HashMap<>();
        user.put("email", "udsfgsdfser@iuyig.com");
        user.put("name", "sdsdfgfdf");
        user.put("password", "sdsadffgd");
        user.put("time", System.currentTimeMillis()); // Save the current time
        if (person("Person", String.valueOf(id), user)) {
            System.out.println("User data has been pushed to Firebase!");
        } else {
            System.out.println("Failed to push user data to Firebase.");
        }
        launch();
    }
}
