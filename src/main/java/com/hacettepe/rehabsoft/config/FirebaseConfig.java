package com.hacettepe.rehabsoft.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/hacettepe-rehabsoft-firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(
//                        new ClassPathResource("hacettepe-rehabsoft-firebase.json")
//                        .getInputStream());
//        FirebaseOptions firebaseOptions = FirebaseOptions
//                .builder()
//                .setCredentials(googleCredentials)
//                .build();
        FirebaseApp app = FirebaseApp.initializeApp(options, "my-app");
        return FirebaseMessaging.getInstance(app);
    }


}
