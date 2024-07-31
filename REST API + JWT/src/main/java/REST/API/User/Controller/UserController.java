package REST.API.User.Controller;

import REST.API.User.Service.UserService;
import REST.API.global.Response.ResponseDto;
import REST.API.User.domain.dto.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  @PostMapping("/join")
  public ResponseEntity<ResponseDto<String>> join(@RequestBody UserJoinRequest dto) {
    userService.join(dto.getUserName(), dto.getPassword());
    return ResponseEntity.ok().body(ResponseDto.of("회원가입 성공"));
  }

}
