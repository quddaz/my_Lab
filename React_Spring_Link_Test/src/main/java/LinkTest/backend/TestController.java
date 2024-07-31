package LinkTest.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/api/test")
  public String hello() {
    System.out.println("호출확인");
    return "테스트입니다.";
  }

}
