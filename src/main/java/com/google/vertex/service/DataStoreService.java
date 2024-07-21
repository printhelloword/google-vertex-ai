package com.google.vertex.service;

import com.google.vertex.config.GoogleConsoleConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class DataStoreService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleConsoleConfig googleConsoleConfig;

    public String uploadFile(MultipartFile file) {
        String response = "null";
        try {
            response = processRequest(file);
        } catch (Exception e) {
            log.info("Fail Make Google Api Call : {}", e);
        }

        return response;
    }
/*

    public String importToMediaDataStore(MultipartFile file) {
        String getImportIntoMediaDataStoreUrl();
    }
*/

    private String processRequest(MultipartFile multipartFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String token = "static-token";
        headers.set("Authorization", "Bearer " + token);

        File file = new File(multipartFile.getName());

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (Exception e) {
            log.info("fail convert file");
        }

        String finalUrl = getUploadObjectUrl(multipartFile.getOriginalFilename());
        log.info("generated url: {}", finalUrl);
        InputStream inputStream = new FileSystemResource(new File(file.getPath())).getInputStream();
        byte[] binaryData = IOUtils.toByteArray(inputStream);
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(binaryData, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, requestEntity, String.class);
        /**/

        return response.getBody();
    }

    private String getUploadObjectUrl(String fileName) {
        return "https://storage.googleapis.com/upload/storage/v1/b/" + googleConsoleConfig.getBucketName() + "/o?uploadType=media&name=" + fileName;
    }

}
