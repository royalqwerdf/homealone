//package com.elice.homealone.global.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//    //application properties나 환경 변수에서 값을 가져옴
//    @Value("${FIREBASE_CONFIG_PATH}")
//    private String firebaseConfigPath;
//
//    //firebase초기화
//    @PostConstruct
//    public void init() throws IOException {
//        System.out.println("Firebase config path: " + firebaseConfigPath);
//        try (FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath)) {
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)) //자격 증명 설정
//                    .setStorageBucket("homealone-73965.appspot.com") //스토리지 버킷 설정
//                    .setProjectId("homealone-73965")  //프로젝트 ID설정
//                    .build();
//
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);   //firebaseApp(Firebase SDK를 사용하는 모든 서비스 사용가능하게 함)이 초기화 되지 않은 경우 초기화
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//       //firebase storage를 빈으로 설정
//    @Bean
//    public Storage firebaseStorage() throws IOException {
//        try (FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath)) {
//            return StorageOptions.newBuilder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))  //자격 설정
//                    .build()
//                    .getService();  //서비스 객체 반환
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//}

