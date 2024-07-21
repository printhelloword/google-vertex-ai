package com.google.vertex.service;

import com.google.vertex.config.GoogleConsoleConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class DataStoreService {
    private final GoogleConsoleConfig googleConsoleConfig;
    private final GoogleApiRestClient googleApiRestClient;

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
