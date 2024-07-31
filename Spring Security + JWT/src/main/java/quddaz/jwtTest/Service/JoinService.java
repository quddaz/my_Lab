package quddaz.jwtTest.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import quddaz.jwtTest.Entity.DTO.JoinDTO;
import quddaz.jwtTest.Entity.UserEntity;
import quddaz.jwtTest.Repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JoinService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  public void joinProcess(JoinDTO joinDTO) {

    String username = joinDTO.getUsername();
    String password = joinDTO.getPassword();

    Boolean isExist = userRepository.existsByUsername(username);

    if (isExist) {

      return;
    }

    UserEntity data = new UserEntity();

    data.setUsername(username);
    data.setPassword(bCryptPasswordEncoder.encode(password));
    data.setRole("ROLE_ADMIN");

    userRepository.save(data);
  }
}