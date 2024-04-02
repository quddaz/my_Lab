package quddaz.jwtTest.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class JwtToken {
  private String grantType; //JWT 인증 타입
  private String accessToken;
  private String refreshToken;
}
