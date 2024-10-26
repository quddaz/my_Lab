package oauth.kakao.auth.application;


import lombok.RequiredArgsConstructor;
import oauth.kakao.auth.application.jwt.JwtTokenProvider;
import oauth.kakao.auth.dto.request.LoginRequest;
import oauth.kakao.auth.dto.request.ReissueRequest;
import oauth.kakao.auth.dto.response.LoginResponse;
import oauth.kakao.auth.dto.response.ReissueResponse;
import oauth.kakao.auth.exception.LoginTypeNotSupportException;
import oauth.kakao.auth.exception.TokenNotValidException;
import oauth.kakao.auth.exception.errorCode.AuthErrorCode;
import oauth.kakao.auth.util.SocialLoginProvider;
import oauth.kakao.member.domain.Member;
import oauth.kakao.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final Map<String, SocialLoginProvider> loginProviders;

    /**
     * 유저가 로그인 or 회원 가입 - social 로그인 access token 과 refresh 토큰을 발급
     */
    public LoginResponse signIn(LoginRequest loginRequest, String provider) {

        //사용자 정보 가져오기
        Member member = getUserDataFromPlatform(loginRequest.accessToken(), provider);

        //로그인 및 회원가입
        Member authenticatedMember = memberRepository.findBySocialId(member.getSocialId())
            .orElseGet(() -> memberRepository.save(member));

        //token 만들기
        String accessToken = jwtTokenProvider.createAccessToken(authenticatedMember);
        String refreshToken = jwtTokenProvider.createRefreshToken(authenticatedMember);

        return LoginResponse.of(accessToken, refreshToken);
    }

    private Member getUserDataFromPlatform(String accessToken, String providerInfo) {
        SocialLoginProvider signInProvider = loginProviders.get(providerInfo + "LoginProvider");
        if (signInProvider == null) {
            throw new LoginTypeNotSupportException(AuthErrorCode.LOGIN_TYPE_NOT_SUPPORT);
        }
        return signInProvider.getUserData(accessToken);
    }

    public ReissueResponse reissueAccessToken(ReissueRequest reissueRequest) {

        String refreshToken = reissueRequest.refreshToken();

        if (jwtTokenProvider.validateToken(refreshToken)) {
            Member member = jwtTokenProvider.getMember(refreshToken);
            String accessToken = jwtTokenProvider.createAccessToken(member);

            return ReissueResponse.from(accessToken);
        } else {
            throw new TokenNotValidException(AuthErrorCode.TOKEN_NOT_VALID);
        }
    }
}