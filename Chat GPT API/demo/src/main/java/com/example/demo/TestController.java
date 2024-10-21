package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final ChatGptService chatGptService;

    @GetMapping(value = "/gpt")
    public ResponseEntity<?> getGpt(@RequestBody String prompt){
        return ResponseEntity.ok().body(chatGptService.getChatResponse(prompt));
    }
}
