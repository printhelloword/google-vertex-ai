package com.vertex.bridging.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.vertex.bridging.service.ChatAgentService;
import com.vertex.bridging.service.DialogFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat-agent")
public class ChatAgentController {

    private final ChatAgentService chatAgentService;

    @GetMapping()
    public String getResponse(@RequestParam("input") String input,
                              @RequestParam("lang") String language) {
        try {
            return chatAgentService.getDialogFlowResponse(input, language);
        } catch (Exception e) {
            log.info("Error : ", e);
        }
        return "fail";
    }

}
