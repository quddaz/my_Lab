package JWT.OAuth20.global.jwt.refresh.service;

import JWT.OAuth20.global.jwt.refresh.entity.Refresh;
import JWT.OAuth20.global.jwt.refresh.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final long RefreshTokenRemiteTime = 1000L * 60 * 60 * 24; // 1일

    //새로운 Refresh 토큰 생성해서 저장
    public void addRefresh(String username, String refresh){
        Date date = new Date(System.currentTimeMillis() + RefreshTokenRemiteTime);
        Refresh refreshEntity = Refresh.builder()
            .username(username)
            .refresh(refresh)
            .expiration(date.toString())
            .build();
        refreshRepository.save(refreshEntity);
    }
    //저장소에서 Refresh 토큰이 있는지 검사
    public Boolean existsByRefresh(String refresh) {
        return refreshRepository.existsByRefresh(refresh);
    }
    public Boolean existsByUsername(String username) {return refreshRepository.existsByUsername(username);}
    public void deleteByRefresh(String refresh) {
        refreshRepository.deleteByRefresh(refresh);
    }
    public void deleteByUsername(String username){refreshRepository.deleteByUsername(username);}
}
