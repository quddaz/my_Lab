package quddaz.myblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quddaz.myblog.domain.Member;
import quddaz.myblog.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true) // 모든 메서드에 읽기 전용 트랜잭션 적용
public class MemberService {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional // 쓰기 작업에 대한 트랜잭션 적용
  public void join(Member member){
    member.setPassword(passwordEncoder.encode(member.getPassword()));
    memberRepository.save(member);
  }

  /**
   * 회원가입 시 동일한 회원 아이디가 있는지 검사합니다.
   * @param userName
   * @return
   */
  public boolean checkDuplicateUsername(String userName){
    Member member = memberRepository.findByUserName(userName);
    return member != null;
  }

  public Optional<Member> findMemberByUserName(String userName) {
    return Optional.ofNullable(memberRepository.findByUserName(userName));
  }
}
