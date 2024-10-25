package oauth.kakao.auth.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthUser(
    Long memberId,
    String socialId,
    String name,
    String email,
    List<String> roles
) {
}