
package com.elice.homealone.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseConfig {

    /*
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/serviceAccountKey");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("homealone-adce9.appspot.com")
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(options);

        return app;
    }

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        return FirebaseAuth.getInstance(firebaseApp());
    }

    @Bean
    public Bucket bucket() throws IOException {
        return StorageClient.getInstance(firebaseApp()).bucket();
    }
     */

}

