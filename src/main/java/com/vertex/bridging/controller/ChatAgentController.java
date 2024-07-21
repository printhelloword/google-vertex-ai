package com.vertex.bridging.controller;

import com.vertex.bridging.service.ChatAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/chat-agent", produces = "application/json")
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
