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
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Bucket bucket = StorageClient.getInstance().bucket(bucketName);
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        String fileUrl = blob.getMediaLink();
        Response.URLResponse urlResponse = new Response.URLResponse(fileUrl);
        return ResponseEntity.ok(urlResponse);
    }
}
