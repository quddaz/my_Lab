package com.example.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final  ApiFeignClient apiFeignClient;
    @Value("${api-key}")
    private String serviceKey;

    @GetMapping("/get")
    public ResponseEntity<?> get(
        @RequestParam("lawdCd") String lawdCd,
        @RequestParam("dealYmd") String dealYmd) {
        try {
            String response = apiFeignClient.getAptTradeData(serviceKey, lawdCd, dealYmd);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching apartment trade data", e);
            return ResponseEntity.status(500).body("Failed to fetch data");
        }
    }
}
