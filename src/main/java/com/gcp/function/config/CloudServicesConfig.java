package com.gcp.function.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CloudServicesConfig {

    private static final GoogleCredentials GOOGLE_CREDENTIALS;
    private static final FirebaseOptions FIREBASE_OPTIONS;
    private static final FirebaseApp FIREBASE_APP;
    public static final FirebaseAuth FIREBASE_AUTH;

    static {

        GOOGLE_CREDENTIALS = getGoogleCredentials();

        FIREBASE_OPTIONS = FirebaseOptions.builder().setCredentials(GOOGLE_CREDENTIALS).build();

        FIREBASE_APP = FirebaseApp.initializeApp(FIREBASE_OPTIONS);

        FIREBASE_AUTH = FirebaseAuth.getInstance(FIREBASE_APP);

    }

    @SneakyThrows
    private static GoogleCredentials getGoogleCredentials() {
        return GoogleCredentials.getApplicationDefault();
    }

}
