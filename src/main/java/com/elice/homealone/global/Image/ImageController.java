package com.elice.homealone.global.Image;

import com.elice.homealone.global.exception.Response;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;
    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<Response.URLResponse> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename(); //파일 이름을 고유한 UUID와 원래 파일 이름을 조합하여 생성
        Bucket bucket = StorageClient.getInstance().bucket(bucketName);  //버킷 인스턴스를 가져옴
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType()); //스토리지에 파일 업로드.

        String fileUrl = blob.getMediaLink();  //업로드한 파일의 url을 가져온다.
        Response.URLResponse urlResponse = new Response.URLResponse(fileUrl); //url로 응답객체 생성
        return ResponseEntity.ok(urlResponse); //응답
    }
}
