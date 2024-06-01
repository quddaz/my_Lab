package REST.API.Service;

import REST.API.Exception.AppException;
import REST.API.Exception.ErrorCode;
import REST.API.Respository.UserRepository;
import REST.API.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;
  @Transactional
  public void join(String userName, String password){
    // USERNAME 중복체크
    userRepository.findByUserName(userName)
        .ifPresent(user -> {
          throw new AppException(ErrorCode.USERNAME_DUPLICATED,userName+ "는 이미 있습니다.");
    });
    // 저장
    User user = User.builder()
            .userName(userName)
            .password(bCryptPasswordEncoder.encode(password))
            .build();
    userRepository.save(user);
  }
}
