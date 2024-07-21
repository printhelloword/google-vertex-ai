package com.google.vertex.controller;

import com.google.vertex.service.DataStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/data-storage", produces = "application/json")
public class DataStoreController {

    private final DataStoreService dataStoreService;

    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("bucket") String bucketName) {
        try {
            return dataStoreService.uploadToDataStore(file, bucketName);
        }catch (Exception e){
            log.info("Error : ", e);
        }
        return "fail";
    }

}
