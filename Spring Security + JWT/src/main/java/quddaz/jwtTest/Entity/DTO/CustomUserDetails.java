package quddaz.jwtTest.Entity.DTO;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quddaz.jwtTest.Entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 스프링 시큐리티에서 사용자의 정보를 표현하는데 사용
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final UserEntity userEntity;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Collection<GrantedAuthority> collection = new ArrayList<>();

    collection.add(new GrantedAuthority() {

      @Override
      public String getAuthority() {

        return userEntity.getRole();
      }
    });

    return collection;
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