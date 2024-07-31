package session.oauth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import session.oauth.DTO.OAuth2Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final OAuth2Response oAuth2Response;
    private final String role;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> role);
        return collection;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    @Override
    public String getUsername() {
        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
    }

    // UserDetails methods
    @Override
    public String getPassword() {
        return null; // Not applicable for OAuth2
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getter methods for Thymeleaf with functional approach
    public Supplier<OAuth2Response> getOAuth2ResponseSupplier() {
        return () -> oAuth2Response;
    }

    public Supplier<String> getRoleSupplier() {
        return () -> role;
    }
}
