package stomp.test.domain.chatMessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stomp.test.domain.chatMessage.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);  // 특정 방의 메시지를 찾는 메서드
}