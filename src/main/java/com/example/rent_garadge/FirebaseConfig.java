package com.example.rent_garadge;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {
    public static Firestore db;
    private final static String cred_path = "src/main/java/com/example/rent_garadge/garage-6f5fe-firebase-adminsdk-v6dcj-bae29d0410.json";

    public static void firestore_connection() {
        try {
            // Initialize Firebase with the service account
            FileInputStream serviceAccount = new FileInputStream(cred_path);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Initialize the Firebase app only once
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            // Get Firestore instance
            db = FirestoreClient.getFirestore();
            System.out.println("Connected to Firestore");

        } catch (IOException e) {
            System.out.println("Error initializing Firebase: " + e.getMessage());
        }
    }
}
