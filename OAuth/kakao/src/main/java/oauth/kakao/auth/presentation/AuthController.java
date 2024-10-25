package oauth.kakao.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oauth.kakao.auth.application.AuthService;
import oauth.kakao.auth.application.jwt.JwtTokenProvider;
import oauth.kakao.auth.dto.request.LoginRequest;
import oauth.kakao.auth.dto.request.ReissueRequest;
import oauth.kakao.auth.dto.response.LoginResponse;
import oauth.kakao.auth.dto.response.ReissueResponse;
import oauth.kakao.global.dto.ResponseTemplate;
import oauth.kakao.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @Operation(summary = "소셜 로그인 진행", description = "소셜 로그인 진행")
    @PostMapping("/login/{provider}")
    public ResponseEntity<ResponseTemplate<Object>> socialLogin(@PathVariable String provider,
                                                                @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.signIn(request, provider);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseTemplate.from(loginResponse));
    }

    @Operation(summary = "access token 재발급", description = "access token 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ResponseTemplate<Object>> reissue(@RequestBody ReissueRequest request) {
        ReissueResponse reissueResponse = authService.reissueAccessToken(request);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseTemplate.from(reissueResponse));
    }
}
