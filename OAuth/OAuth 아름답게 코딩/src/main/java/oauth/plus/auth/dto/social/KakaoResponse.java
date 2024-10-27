package oauth.plus.auth.dto.social;

import oauth.plus.member.domain.type.OAuthType;

import java.util.Map;
import java.util.Map;

import java.util.Map;
import java.util.Optional;

public record KakaoResponse(Map<String, Object> attribute) implements OAuth2Response {

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public OAuthType getProvider() {
        return OAuthType.KAKAO;
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(attribute.get("id"))
            .map(Object::toString)
            .orElse(null);
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(attribute.get("kakao_account"))
            .map(account -> ((Map<String, Object>) account).get("email"))
            .map(Object::toString)
            .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(attribute.get("properties"))
            .map(properties -> ((Map<String, Object>) properties).get("nickname"))
            .map(Object::toString)
            .orElse(null);
    }

    @Override
    public String getProfileImage() {
        return Optional.ofNullable(attribute.get("properties"))
            .map(properties -> ((Map<String, Object>) properties).get("profile_image"))
            .map(Object::toString)
            .orElse(null);
    }
}

