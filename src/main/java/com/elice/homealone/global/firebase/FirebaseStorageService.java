package com.elice.homealone.global.firebase;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

@Service
public class FirebaseStorageService {

    public void deleteImage(String fileName) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        BlobId blobId = BlobId.of("homealone-adce9.appspot.com", "images/" + fileName);
        storage.delete(blobId);
    }
}
