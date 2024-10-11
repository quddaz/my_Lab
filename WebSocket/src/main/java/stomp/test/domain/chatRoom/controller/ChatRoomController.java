package stomp.test.domain.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import stomp.test.domain.chatRoom.entity.ChatRoom;
import stomp.test.domain.chatRoom.service.ChatRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 모든 채팅방 조회
    @GetMapping
    public List<ChatRoom> findAllRooms() {
        return chatRoomService.findAllRooms();
    }

    // 채팅방 생성
    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomService.createRoom(name);
    }
}