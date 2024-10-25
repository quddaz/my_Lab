package oauth.plus;

import lombok.RequiredArgsConstructor;
import oauth.plus.auth.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/my")
    public ResponseEntity<?> getMY(@AuthenticationPrincipal AuthUser authUser){
        return ResponseEntity.ok().body(authUser);
    }
}
