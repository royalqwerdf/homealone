package com.elice.homealone.global.Image;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class ImageService {

    private final Storage storage;
    private final String bucketName = "homealone-73965.appspot.com";


    @Autowired
    public ImageService(Storage storage) {
        this.storage = storage;
    }


    public void deleteImage(String imageUrl){
        try {
            // URL에서 파일 이름과 쿼리 파라미터를 분리하여 파일 이름만 추출
            String decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.name());
            String fileName = decodedUrl.substring(decodedUrl.lastIndexOf('/') + 1);
            if (fileName.contains("?")) {
                fileName = fileName.substring(0, fileName.indexOf('?'));
            }

            BlobId blobId = BlobId.of(bucketName, fileName);
            boolean deleted = storage.delete(blobId);
            if (deleted) {
                System.out.println("File deleted successfully: " + fileName);
            } else {
                System.out.println("File not found: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
