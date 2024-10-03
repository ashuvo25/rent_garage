package com.example.rent_garadge;

//import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.auth.FirebaseToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseConfig {
    private static String cred = "src/main/java/com/example/rent_garadge/garage-6f5fe-firebase-adminsdk-v6dcj-bae29d0410.json";
    public static Firestore db;

    public static void firestore_connection() {
        try {
            // Initialize Firebase with the service account
            FileInputStream serviceAccount = new FileInputStream(cred);
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

    public static String datainput(String collection, String document, Map<String, Object> data) {
        firestore_connection();
        try {
            // Check if the document already exists
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot documentSnapshot = future.get();

            if (documentSnapshot.exists()) {
                // Document already exists
                System.out.println("Document already exists");
                return "duplicate"; // Indicate that the document already exists
            }

            // If the document doesn't exist, proceed to store the new data
            ApiFuture<WriteResult> result = docRef.set(data);
            WriteResult writeResult = result.get();
            System.out.println("Data stored at: " + writeResult.toString());
            return "signup";

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "other";
    }

    public static String verifyEmailAndPassword(String collection, String email, String inputPassword) {
        firestore_connection();
        try {
            // Get a reference to the document using the email as the document ID
            DocumentReference docRef = db.collection(collection).document(email);
            System.out.println(email);
            // Asynchronously retrieve the document
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot documentSnapshot = future.get();

            if (documentSnapshot.exists()) {
                // Document exists, retrieve the stored password
                String storedPassword = documentSnapshot.getString("password");

                if (storedPassword != null && storedPassword.equals(inputPassword)) {
                    // Passwords match
                    System.out.println("Login successful.");
                    return "signin"; // Email and password match
                } else {
                    // Passwords do not match
                    System.out.println("Incorrect password.");
                    return "incorrect_pass"; // Password is incorrect
                }
            } else {
                // Email does not exist in the database
                System.out.println("Email not found.");
                return "no_email";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return e.getMessage();
        }
    }

    public static Map<String, Object> getUserData(String collection, String email) {
        firestore_connection();
        try {
            DocumentReference docRef = db.collection(collection).document(email);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot documentSnapshot = future.get();

            if (documentSnapshot.exists()) {
                // Return the user data as a map
                return documentSnapshot.getData();
            } else {
                // If no document is found, return null or an empty map
                return null; // or return Collections.emptyMap();
            }
        } catch (Exception e) {
            // Handle exceptions, can also return null or an empty map with error handling
            return null;
        }
    }


    // Function to verify the Google ID token and create a user in Firestore
    public static String googleSignUp(String idTokenString) {
        firestore_connection(); // Make sure Firestore connection is initialized

        try {
            // Verify the  ID token using Firebase Auth
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();
            String picture = decodedToken.getPicture();

            // Check if the user already exists in Firestore
            DocumentReference docRef = db.collection("users").document(uid);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot documentSnapshot = future.get();

            if (documentSnapshot.exists()) {
                System.out.println("User already exists.");
                return "user_exists";  // User already exists
            }

            // If the user does not exist, create a new user in Firestore
            Map<String, Object> userData = new HashMap<>();
            userData.put("uid", uid);
            userData.put("email", email);
            userData.put("name", name);
            userData.put("picture", picture);
            userData.put("created_at", System.currentTimeMillis());

            ApiFuture<WriteResult> result = docRef.set(userData);
            System.out.println("User signed up successfully: " + result.get().getUpdateTime());
            return "signup_success";

        } catch (FirebaseAuthException e) {
            System.out.println("Invalid ID token: " + e.getMessage());
            return "invalid_token";
        } catch (Exception e) {
            System.out.println("Error signing up user: " + e.getMessage());
            return "signup_error";
        }
    }








//    public static void signup() {
//
//        int id = (int) (Math.random() * 10000);
//        Map<String, Object> user = new HashMap<>();
//        user.put("email", "udsfgsdfser@iuyig.com");
//        user.put("name", "adnan");
//        user.put("password", "sdsadffgd");
//        user.put("time", System.currentTimeMillis()); // Save the current time
//        if (datainput("Person", String.valueOf(id), user)) {
//            System.out.println("User data has been pushed to Firebase!");
//        } else {
//            System.out.println("Failed to push user data to Firebase.");
//        }
//    }

}
