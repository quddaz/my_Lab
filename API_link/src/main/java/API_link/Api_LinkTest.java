package API_link;


import API_link.DTO.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Api_LinkTest {

  @Value("${key}")
  private String secretKey;

  private final String apiUrl = "https://apis.data.go.kr/B552583/job/job_list";
  @GetMapping("/test")
  public ResponseEntity<String> ApiTest(
      @RequestParam(value = "page") String page
  ) {
    try {
      // API 호출 및 응답 받기
      RestTemplate restTemplate = new RestTemplate();
      String url = apiUrl + "?serviceKey=" + secretKey + "&pageNo=" + page + "&numOfRows=400";
      String responseEntity = String.valueOf(restTemplate.getForEntity(url, String.class));

      return new ResponseEntity<>(responseEntity,HttpStatus.OK );
    } catch (Exception e) {
      log.error("API 호출 중 에러 발생", e);
      // 예외 발생 시 에러 응답 반환
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/jobs")
  public ResponseEntity<ResponseData.Items> callApi(
      @RequestParam(value = "region") String region
      ,@RequestParam(value = "empType") String empType
  ) {
    log.info("API 호출:  region={}, empType={}",region,empType);

    try {
      // API 호출 및 응답 받기
      RestTemplate restTemplate = new RestTemplate();
      String url = apiUrl + "?serviceKey=" + secretKey + "&pageNo=1" + "&numOfRows=200";
      ResponseEntity<String> reposne = restTemplate.getForEntity(url, String.class);
      System.out.println(reposne);
      ResponseEntity<ResponseData> responseEntity = restTemplate.getForEntity(url, ResponseData.class);

      // API 응답 확인 및 처리
        ResponseData responseData = responseEntity.getBody();
        if (responseData != null && responseData.getBody() != null && responseData.getBody().getItems() != null) {
          List<ResponseData.Item> filteredItems = new ArrayList<>();
          String  region_targetPrefix = region.split(" ")[0];
          String empType_targetPrefix = empType.split(" ")[0];
          for (ResponseData.Item item : responseData.getBody().getItems().getItem()) {
            if (item.getCompAddr() != null && item.getCompAddr().startsWith(region_targetPrefix)&&item.getEmpType().startsWith(empType_targetPrefix)) {
              filteredItems.add(item);
            }
          }
          responseData.getBody().getItems().setItem(filteredItems);
        }
        return new ResponseEntity<>(responseData.getBody().getItems(), HttpStatus.OK);
    } catch (HttpServerErrorException e) {
      // 외부 API 서버에서 에러 응답 반환
      log.error("외부 API 서버에서 에러 응답 반환: {}", e.getStatusCode());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      // 그 외의 예외 발생
      log.error("API 호출 중 에러 발생", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
