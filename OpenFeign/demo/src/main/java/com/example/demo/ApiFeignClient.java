package com.example.demo;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "apiFeignClient", url = "https://apis.data.go.kr")
public interface ApiFeignClient {

    @GetMapping("/1613000/RTMSDataSvcAptTrade/getRTMSDataSvcAptTrade")
    String getAptTradeData(@RequestParam("serviceKey") String serviceKey,
                           @RequestParam("LAWD_CD") String lawdCd,
                           @RequestParam("DEAL_YMD") String dealYmd);
}