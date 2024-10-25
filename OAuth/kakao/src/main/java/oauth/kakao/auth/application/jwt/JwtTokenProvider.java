package oauth.kakao.auth.application.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oauth.kakao.member.domain.Member;
import oauth.kakao.member.exception.MemberNotFoundException;
import oauth.kakao.member.exception.errorCode.MemberErrorCode;
import oauth.kakao.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;
    private Key key;
    private static final String MEMBER_ROLE = "role";

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Member member) {
        long now = (new Date()).getTime();

        Date accessValidity = new Date(now + jwtProperties.getAccessTokenExpiration());

        log.info("expire: {}", accessValidity);

        return Jwts.builder()
            // 토큰의 발급 시간을 기록
            .setIssuedAt(new Date(now))
            .setExpiration(accessValidity)
            // 토큰을 발급한 주체를 설정
            .setIssuer(jwtProperties.getIssuer())
            .setSubject(member.getId().toString())
            .addClaims(Map.of(MEMBER_ROLE, member.getRole().name()))
            // 토큰이 JWT 타입 명시
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Member member) {
        long now = (new Date()).getTime();

        Date refreshValidity = new Date(now + jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
            // 토큰의 발급 시간을 기록
            .setIssuedAt(new Date(now))
            .setExpiration(refreshValidity)
            // 토큰을 발급한 주체를 설정
            .setIssuer(jwtProperties.getIssuer())
            .setSubject(member.getId().toString())
            .addClaims(Map.of(MEMBER_ROLE, member.getRole().name()))
            // 토큰이 JWT 타입 명시
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public boolean validateToken(final String token) {
        try {
            log.info("now date: {}", new Date());
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            log.info("expire date: {}", claims.getBody().getExpiration());
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    public Member getMember(String token) {
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token).getBody().getSubject());

        log.info("in getMember() socialId: {}", id);

        return memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
