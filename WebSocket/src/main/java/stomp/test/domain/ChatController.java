package stomp.test.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import stomp.test.domain.chatMessage.entity.ChatMessage;
import stomp.test.domain.chatMessage.service.ChatMessageService;
import stomp.test.domain.chatRoom.ChatMessageRequest;
import stomp.test.domain.chatRoom.entity.ChatRoom;
import stomp.test.domain.chatRoom.service.ChatRoomService;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/sendMessage/{roomId}")
    @SendTo("/chat/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable String roomId, ChatMessageRequest request) {
        ChatRoom chatRoom = chatRoomService.findRoomById(roomId);
        return chatMessageService.saveMessage(chatRoom, request.sender(), request.message());
    }
}