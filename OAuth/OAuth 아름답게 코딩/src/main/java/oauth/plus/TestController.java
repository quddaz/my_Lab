package oauth.plus;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oauth.plus.auth.application.AuthService;
import oauth.plus.auth.dto.AuthUser;
import oauth.plus.global.dto.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final AuthService authService;
    @GetMapping("/my")
    public ResponseEntity<?> getMY(@AuthenticationPrincipal AuthUser authUser){
        return ResponseEntity.ok().body(authUser);
    }
    @GetMapping("/reissue")
    public ResponseEntity<ResponseTemplate<?>> reIssueToken(
        @CookieValue(name = "REFRESH_TOKEN") String refreshToken, HttpServletResponse response) {

        authService.reIssueToken(refreshToken, response);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
