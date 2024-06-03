package quddaz.jwtTest.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quddaz.jwtTest.auth.CustomUserDetails;
import quddaz.jwtTest.Entity.UserEntity;
import quddaz.jwtTest.Repository.UserRepository;

/**
 * 스프링 시큐리티에서 사용자의 인증 정보를 로드하는데 사용
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //DB에서 조회
    UserEntity userData = userRepository.findByUsername(username);

    if (userData != null) {

      //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
      return new CustomUserDetails(userData);
    }

    return null;
  }
}