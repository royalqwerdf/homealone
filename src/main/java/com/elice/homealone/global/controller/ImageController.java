package com.elice.homealone.global.controller;


import com.elice.homealone.global.exception.Response;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class ImageController {
    @PostMapping("/upload")
    public ResponseEntity<Response.URLResponse> uploadImage(@RequestParam("file")MultipartFile file) throws IOException{
        String fileName = UUID.randomUUID().toString()+ "-" +file.getOriginalFilename();
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(fileName,file.getBytes(), file.getContentType());

        String fileUrl = blob.getMediaLink();
        Response.URLResponse urlResponse = new Response.URLResponse(fileUrl);
        return ResponseEntity.ok(urlResponse);
    }

}
