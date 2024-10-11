package stomp.test.domain.chatRoom;

public record ChatMessageRequest(
    String sender,  // 메시지 작성자
    String message) {
}