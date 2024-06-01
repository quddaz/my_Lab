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
          throw new AppException(ErrorCode.USERNAME_DUPLICATED);
    });
    // 저장
    User user = User.builder()
            .userName(userName)
            .password(bCryptPasswordEncoder.encode(password))
            .build();
    userRepository.save(user);
  }
  public String login(String userName, String password){
    //userName 없음
    User user = userRepository.findByUserName(userName)
        .orElseThrow(() ->
          new AppException(ErrorCode.FAILD_LOGIN));
    //password 틀림
    if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
      throw new AppException(ErrorCode.FAILD_LOGIN);
    }
    //앞에서 Exception 안났으면 토큰 발행
    return "token";
  }
}
