package stomp.test.domain.chatMessage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stomp.test.domain.chatMessage.entity.ChatMessage;
import stomp.test.domain.chatMessage.repository.ChatMessageRepository;
import stomp.test.domain.chatRoom.entity.ChatRoom;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    // 채팅방의 모든 메시지 조회
    public List<ChatMessage> findMessagesByRoomId(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }

    // 메시지 저장
    public ChatMessage saveMessage(ChatRoom chatRoom, String sender, String message) {
        ChatMessage chatMessage = ChatMessage.create(chatRoom, sender, message);
        return chatMessageRepository.save(chatMessage);
    }
}