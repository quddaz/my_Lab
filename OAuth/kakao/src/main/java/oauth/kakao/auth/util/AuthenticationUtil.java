package oauth.kakao.auth.util;

import oauth.kakao.auth.dto.AuthUser;
import oauth.kakao.member.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationUtil {

    public static void makeAuthentication(Member member) {
        // Authentication 정보 만들기
        AuthUser authUser = AuthUser.builder()
            .memberId(member.getMemberId())
            .socialId(member.getSocialId())
            .email(member.getEmail())
            .roles(Collections.singletonList(member.getRoleKey()))
            .build();

        // ContextHolder 에 Authentication 정보 저장
        Authentication auth = AuthenticationUtil.getAuthentication(authUser);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private static Authentication getAuthentication(AuthUser authUser) {
        List<GrantedAuthority> grantedAuthorities = authUser.roles().stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(authUser, "", grantedAuthorities);
    }
}