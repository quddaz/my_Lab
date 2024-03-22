package quddaz.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quddaz.myblog.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
  Member findByUserName(String userName);
}
