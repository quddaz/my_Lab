package quddaz.jwtTest.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quddaz.jwtTest.Entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 스프링 시큐리티에서 사용자의 정보를 표현하는데 사용
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final UserEntity userEntity;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 사용자가 가진 권한을 담을 리스트
    List<GrantedAuthority> authorities = new ArrayList<>();

    // 사용자 엔티티로부터 권한 정보를 가져와서 권한 객체로 변환하여 리스트에 추가
    String role = userEntity.getRole();
    authorities.add(new SimpleGrantedAuthority(role));

    return authorities;
  }

  @Override
  public String getPassword() {

    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {

    return userEntity.getUsername();
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
}