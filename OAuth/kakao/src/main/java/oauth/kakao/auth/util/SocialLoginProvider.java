package oauth.kakao.auth.util;

import oauth.kakao.member.domain.Member;

public interface SocialLoginProvider {
    Member getUserData(String accessToken);
}