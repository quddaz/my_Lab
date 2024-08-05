package REST.API.User.Service;

import REST.API.global.Exception.BaseException.AppException;
import REST.API.global.Exception.errorCode.UserErrorCode;
import REST.API.User.Respository.UserRepository;
import REST.API.User.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
          throw new AppException(UserErrorCode.USERNAME_DUPLICATED);
    });
    // 저장
    userRepository.save(User.builder()
        .userName(userName)
        .password(bCryptPasswordEncoder.encode(password))
        .build());
  }
}
