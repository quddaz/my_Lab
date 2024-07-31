package quddaz.jwtTest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import quddaz.jwtTest.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  Boolean existsByUsername(String username);

  //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
  UserEntity findByUsername(String username);
}