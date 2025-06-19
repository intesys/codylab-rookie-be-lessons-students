package it.intesys.codylab.rookie.lessons.exception;

public class SenderNotInChat extends RuntimeException {
    Long chatId;
        Long senderId;
    public SenderNotInChat(Long chatId, Long senderId) {
        this.chatId = chatId;
        this.senderId = senderId;
    }

    @Override
    public String getMessage() {
        return String.format("Sender %d not in chat %d", senderId, chatId);
    }
}
