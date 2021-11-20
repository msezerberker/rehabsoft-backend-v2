package com.hacettepe.rehabsoft.helper;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
@Component
public class GoogleDriveHelper {

    //@Value("${spring.cloud.gcp.credentials.location}")
    //private String GOOGLE_CREDENTIALS_LOCATION;
    private final String BUCKET_NAME = "spring-bucket-rehabsoft_css";

//    private final Bucket bucket;
//    private final Storage storage;

    private  Bucket bucket;
    private  Storage storage;

    public GoogleDriveHelper() throws IOException, URISyntaxException {
//        InputStream in = getClass().getResourceAsStream("/key_credentials2.json");
//        Credentials credentials = GoogleCredentials
//                .fromStream(in);
//        StorageOptions options = StorageOptions.newBuilder()
//                .setProjectId("focus-antler-316416 ya" +
//                        "")
//                .setCredentials(credentials).build();
//        storage = options.getService();
//        bucket = storage.get(BUCKET_NAME);
    }

    public void saveFile(MultipartFile image, String directoryPath) throws IOException {
        byte[] bytes = image.getBytes();
        Blob blob1 = bucket.create(directoryPath, bytes, "text/plain");
    }

    public byte[] readFileAsByteArray(String filePath){

        // to read file from bucket:
        Blob blob = storage.get(bucket.getName(), filePath);

        //release the blob and free up memory. (since JDBC 4.0)
        return blob.getContent();
    }

    // This method is more efficient than deleting files one by one.
    public void deleteFolder(String folderPath) throws Exception {
        List<StorageBatchResult<Boolean>> results = new ArrayList<>();
        StorageBatch batch = storage.batch();
        try {
            Page<Blob> blobs = storage.list(bucket.getName(), Storage.BlobListOption.currentDirectory(),
                    Storage.BlobListOption.prefix(folderPath));
            for(Blob blob : blobs.iterateAll()) {
                results.add(batch.delete(blob.getBlobId()));
            }
            //batch.submit();
        } catch (Exception exception){
            throw new Exception("Dosyalar silinemedi!");
        }
    }

    public void deleteFile(String filePath){
        storage.delete(bucket.getName(), filePath);
    }

    public Long getSizeOfFile(String filePath){
        return storage.get(bucket.getName(), filePath).getSize();
    }
}


