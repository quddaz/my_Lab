package stomp.test.domain.chatRoom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 채팅방 ID

    private String roomId;  // STOMP에서 사용할 채팅방 식별자

    private String name;  // 채팅방 이름

    public static ChatRoom create(String name) {
        return ChatRoom.builder()
            .roomId(UUID.randomUUID().toString())  // 랜덤 ID 생성
            .name(name)
            .build();
    }
}