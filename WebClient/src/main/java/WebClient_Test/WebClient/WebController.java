package WebClient_Test.WebClient;

import WebClient_Test.WebClient.DTO.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class WebController {
  @Autowired
  private WebClient webClient;

  @Value("${key}")
  private String secretKey;

  @GetMapping("/jobs")
  public Mono<ResponseEntity<List<ResponseData.Item>>> callApi(@RequestParam(value = "local") String local) {
    log.info("API 호출:  local={}", local);

    try {
      // API 호출 및 응답 받기
      return webClient.get()
          .uri(uriBuilder -> uriBuilder
              .queryParam("serviceKey", secretKey)
              .queryParam("pageNo", 1)
              .queryParam("numOfRows", 200)
              .build())
          .retrieve()
          .bodyToMono(ResponseData.class)
          .map(responseData -> {
            // API 응답 확인 및 처리
            if (responseData != null && responseData.getBody() != null && responseData.getBody().getItems() != null) {
              List<ResponseData.Item> filteredItems = responseData.getBody().getItems().getItem().stream()
                  .filter(item -> item.getCompAddr() != null && item.getCompAddr().startsWith(local.split(" ")[0]))
                  .collect(Collectors.toList());
              responseData.getBody().getItems().setItem(filteredItems);
            }
            return ResponseEntity.ok(responseData.getBody().getItems().getItem());
          });
    } catch (Exception e) {
      // 그 외의 예외 발생
      log.error("API 호출 중 에러 발생", e);
      return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
  }
}
