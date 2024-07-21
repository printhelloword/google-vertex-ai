package com.google.vertex.service;

import com.google.vertex.config.GoogleConsoleConfig;
import com.google.vertex.dto.cloudstorage.request.CreateNewBucketRequest;
import com.google.vertex.helper.GoogleCredentialHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


import java.io.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class CloudStorageService {
    private final GoogleApiRestClient googleApiRestClient;
    private final GoogleCredentialHelper googleCredentialHelper;
    private final GoogleConsoleConfig googleConsoleConfig;

    public String createNewBucket(CreateNewBucketRequest createNewBucketRequest) {
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(googleCredentialHelper.getCredential()).setProjectId(googleConsoleConfig.getProjectId())
                .build()
                .getService();
        Bucket bucket = storage.create(BucketInfo.of(createNewBucketRequest.getBucketName()));

        log.info("New Bucket Created : {} ", bucket.getName());

        return bucket.getName();
    }

    public String uploadToDataStore(MultipartFile multipartFile, String bucketName) throws IOException {
        File file = new File(multipartFile.getName());

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (Exception e) {
            log.info("fail convert file");
        }

        String url = buildUrl(multipartFile.getOriginalFilename(), bucketName);
        log.info("generated url: {}", url);
        InputStream inputStream = new FileSystemResource(new File(file.getPath())).getInputStream();
        byte[] binaryData = IOUtils.toByteArray(inputStream);
        return googleApiRestClient.makeApiCall(url, HttpMethod.POST, binaryData);
    }

    private String buildUrl(String fileName, String bucketName) {
        return "https://storage.googleapis.com/upload/storage/v1/b/" + bucketName + "/o?uploadType=media&name=" + fileName;
    }

}
