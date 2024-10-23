package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ChatGptService chatGptService;

    @PostMapping(value = "/gpt")
    public ResponseEntity<?> getGpt(@RequestBody String prompt){
        return ResponseEntity.ok().body(chatGptService.getChatResponse(prompt));
    }
}
