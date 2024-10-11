package stomp.test.domain.chatRoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stomp.test.domain.chatRoom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByRoomId(String roomId);  // roomId로 방을 찾는 메서드
}