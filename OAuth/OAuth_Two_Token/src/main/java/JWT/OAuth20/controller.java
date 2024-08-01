package JWT.OAuth20;

import JWT.OAuth20.global.auth.CustomOAuth2User;
import JWT.OAuth20.global.baseResponse.ResponseDto;
import JWT.OAuth20.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class controller {
    private final MemberRepository memberRepository;
    @GetMapping
    public ResponseEntity<ResponseDto<?>> getUser(@AuthenticationPrincipal CustomOAuth2User customOAuth2User){

        if (customOAuth2User != null) {
            log.info("{}", customOAuth2User.getUsername());
            // 사용자 정보를 반환
            return ResponseEntity.ok(ResponseDto.of(customOAuth2User));
        }
        // 인증되지 않은 경우 401 Unauthorized 반환
        return ResponseEntity.status(401).body(ResponseDto.fail(401, "인증되지 않았습니다."));
    }
}