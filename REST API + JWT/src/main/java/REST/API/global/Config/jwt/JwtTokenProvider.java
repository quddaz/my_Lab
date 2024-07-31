package REST.API.global.Config.jwt;

import REST.API.global.auth.UserDetailsService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final UserDetailsService userDetailsService;
    private final long tokenRemiteTime = 1000L * 60 * 10; // 10분
    private final long RefreshTokenRemiteTime = 1000L * 60 * 60 * 24; // 1일

    public JwtTokenProvider(@Value("${spring.jwt.secret}")String secret, UserDetailsService userDetailsService) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userDetailsService = userDetailsService;
    }
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    //토큰이 만료되었는지 확인한다.
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }
    public String createAccessToken(String category,String username) {

        return Jwts.builder()
            .claim("category", category)
            .claim("username", username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + tokenRemiteTime))
            .signWith(secretKey)
            .compact();
    }
    public String createRefreshToken(String category,String username) {

        return Jwts.builder()
            .claim("category", category)
            .claim("username", username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + RefreshTokenRemiteTime))
            .signWith(secretKey)
            .compact();
    }

}
