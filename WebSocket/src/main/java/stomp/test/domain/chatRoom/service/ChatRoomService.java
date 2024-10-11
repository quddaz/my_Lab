package stomp.test.domain.chatRoom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stomp.test.domain.chatRoom.entity.ChatRoom;
import stomp.test.domain.chatRoom.repository.ChatRoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRooms() {
        return chatRoomRepository.findAll();
    }

    // 채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        return chatRoomRepository.save(chatRoom);
    }

    // 채팅방 찾기
    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }
}