package REST.API.Controller;

import REST.API.Service.UserService;
import REST.API.domain.dto.UserJoinRequest;
import REST.API.domain.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  @PostMapping("/join")
  public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){
    userService.join(dto.getUserName(), dto.getPassword());
    return ResponseEntity.ok().body("회원 가입 성공");
  }
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserLoginRequest dto){
    String token = userService.login(dto.getUserName(), dto.getPassword());
    return ResponseEntity.ok().body("로그인");
  }
}
