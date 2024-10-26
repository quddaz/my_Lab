package com.example.demo;

import com.example.demo.DTO.ChatRequest;
import com.example.demo.DTO.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "chatgpt", url = "https://api.openai.com/v1")
public interface GptFeignClient {

    @PostMapping("/chat/completions")
    ChatResponse createChatCompletion(
        @RequestHeader("Authorization") String authorization,
        @RequestBody ChatRequest chatRequest);
}