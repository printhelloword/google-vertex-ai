package com.google.vertex.controller;

import com.google.vertex.service.ChatAgentService;
import com.vertex.bridging.service.DialogFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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
