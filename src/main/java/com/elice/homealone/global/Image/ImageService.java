//package com.elice.homealone.global.Image;
//
//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class ImageService {
//
//    private final Storage storage;
//    private final String bucketName = "homealone-73965.appspot.com";
//
//    //firebaseConfig클래스에서 빈으로 설정한 storage인스턴스 자동 주입
////    @Autowired
//    public ImageService(Storage storage) {
//        this.storage = storage;
//    }
//
//
//    public void deleteImage(String imageUrl){
//        try {
//            String decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.name());// URL을 디코딩
//            String fileName = decodedUrl.substring(decodedUrl.lastIndexOf('/') + 1); //URL에서 파일 이름을 추출
//            if (fileName.contains("?")) { //파라미터가 포함되 있는 경우 제거
//                fileName = fileName.substring(0, fileName.indexOf('?'));
//            }
//
//            BlobId blobId = BlobId.of(bucketName, fileName);  //blodId생성
//            boolean deleted = storage.delete(blobId);  //스토리지에서 blodId제거
//            if (deleted) {
//                System.out.println("File deleted successfully: " + fileName);
//            } else {
//                System.out.println("File not found: " + fileName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
