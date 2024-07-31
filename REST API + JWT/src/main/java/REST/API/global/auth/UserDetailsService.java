package REST.API.global.auth;

import REST.API.User.Respository.UserRepository;
import REST.API.User.domain.User;
import REST.API.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        // DB에서 사용자 조회
        User user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));


        return new CustomUserDetails(user);
    }
}