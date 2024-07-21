package com.vertex.bridging.controller;

import com.vertex.bridging.service.ChatAgentService;
import com.vertex.bridging.service.DialogFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/dialog-flow")
public class DialogFlowController {

    private final DialogFlowService dialogFlowService;

    @GetMapping()
    public String getResponse(@RequestParam("input") String input) {
        try {
            return dialogFlowService.detectIntentTexts(input);
        } catch (Exception e) {
            log.info("Error : ", e);
        }
        return "fail";
    }

}
