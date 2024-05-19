package API_link;

import DTO.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Api_LinkTest2 {

  @Value("${key}")
  private String secretKey;

  private final String apiUrl = "https://apis.data.go.kr/B552583/job/job_list";

  @GetMapping("/api")
  public ResponseEntity<ResponseData> callApi(
      @RequestParam(value = "page") String page,
      @RequestParam(value = "local") String local
  ) {
    try {
      // API 호출 및 응답 받기
      RestTemplate restTemplate = new RestTemplate();
      String url = apiUrl + "?serviceKey=" + secretKey + "&pageNo=" + page + "&numOfRows=100";
      ResponseEntity<ResponseData> responseEntity = restTemplate.getForEntity(url, ResponseData.class);

      // 받은 local 파라미터를 이용하여 compAddr의 앞 단어를 기준으로 검색
      ResponseData responseData = responseEntity.getBody();
      if (responseData != null && responseData.getBody() != null && responseData.getBody().getItems() != null) {
        List<ResponseData.Item> filteredItems = new ArrayList<>();
        String targetPrefix = local.split(" ")[0]; // local 파라미터를 공백을 기준으로 분리하고 첫 번째 단어를 선택
        for (ResponseData.Item item : responseData.getBody().getItems().getItem()) {
          if (item.getCompAddr().startsWith(targetPrefix)) {
            filteredItems.add(item);
          }
        }
        responseData.getBody().getItems().setItem(filteredItems);
      }

      return new ResponseEntity<>(responseData, responseEntity.getStatusCode());
    } catch (Exception e) {
      e.printStackTrace();
      // 예외 발생 시 에러 응답 반환
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}