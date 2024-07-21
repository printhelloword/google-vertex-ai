package com.google.vertex.controller;

import com.google.vertex.dto.cloudstorage.request.CreateNewBucketRequest;
import com.google.vertex.service.CloudStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/data-storages", produces = "application/json")
public class CloudStorageController {

    private final CloudStorageService cloudStorageService;

    @PostMapping("/buckets")
    public String createNewBucket(@RequestBody CreateNewBucketRequest request) {
        try {
            return cloudStorageService.createNewBucket(request);
        } catch (Exception e) {
            log.info("Error : ", e);
            return e.getMessage();
        }
    }

    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("bucket") String bucketName) {
        try {
            return cloudStorageService.uploadToDataStore(file, bucketName);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
