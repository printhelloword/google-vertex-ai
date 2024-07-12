package com.vertex.bridging.controller;

import com.vertex.bridging.service.DataStoreService;
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
@RequestMapping("/data-storage")
public class DataStoreController {

    private final DataStoreService dataStoreService;

    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return dataStoreService.uploadFile(file);
        }catch (Exception e){
            log.info("Error : ", e);
        }
        return "fail";
    }

}
