package oauth.kakao.auth.dto.request;

public record ReissueRequest(
    String refreshToken
) {
}