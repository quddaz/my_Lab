package stomp.test.domain.chatMessage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import stomp.test.domain.chatRoom.entity.ChatRoom;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 메시지 ID

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;  // 어떤 채팅방의 메시지인지

    private String sender;  // 메시지 보낸 사람

    private String message;  // 메시지 내용

    private LocalDateTime timestamp;  // 메시지 전송 시간

    public static ChatMessage create(ChatRoom chatRoom, String sender, String message) {
        return ChatMessage.builder()
            .chatRoom(chatRoom)
            .sender(sender)
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
