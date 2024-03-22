package quddaz.myblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quddaz.myblog.domain.Member;
import quddaz.myblog.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
  private final MemberRepository memberRepository;

  public void join(Member member){
    memberRepository.save(member);
  }
  public boolean checkDuplicateUsername(String userName){
    Member member = memberRepository.findByUserName(userName);
    return member != null;
  }

}
