package JWT.OAuth20;

import JWT.OAuth20.entity.Member;
import JWT.OAuth20.oauth.dto.CustomOAuth2User;
import JWT.OAuth20.oauth.dto.MemberDTO;
import JWT.OAuth20.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<?> getUser(@AuthenticationPrincipal CustomOAuth2User customOAuth2User){

        if (customOAuth2User != null) {
            Member member = memberRepository.findByUsername(customOAuth2User.getUsername());
            log.info("{}", member.getEmail());
            // 사용자 정보를 반환
            return ResponseEntity.ok(member.getEmail());
        }
        // 인증되지 않은 경우 401 Unauthorized 반환
        return ResponseEntity.status(401).body("Unauthorized");
    }
}