package REST.API.Service;

import REST.API.Respository.UserRepository;
import REST.API.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
  private final UserRepository userRepository;
  @Transactional
  public void join(String userName, String password){
    // USERNAME 중복체크
    userRepository.findByUserName(userName)
        .ifPresent(user -> {
          throw new RuntimeException("는 이미 있습니다.");
    });
    // 저장
    User user = User.builder()
            .userName(userName)
            .password(password)
            .build();
    userRepository.save(user);
  }
}
