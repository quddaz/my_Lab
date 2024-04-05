package quddaz.jwtTest.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quddaz.jwtTest.Entity.DTO.JoinDTO;
import quddaz.jwtTest.Service.JoinService;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

  private final JoinService joinService;


  @PostMapping("/join")
  public String joinProcess(JoinDTO joinDTO) {

    System.out.println(joinDTO.getUsername());
    joinService.joinProcess(joinDTO);

    return "ok";
  }
}