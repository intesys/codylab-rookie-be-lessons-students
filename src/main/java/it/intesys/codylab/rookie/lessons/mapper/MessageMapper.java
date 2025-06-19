package it.intesys.codylab.rookie.lessons.mapper;


import it.intesys.codylab.rookie.lessons.domain.Account;
import it.intesys.codylab.rookie.lessons.domain.Chat;
import it.intesys.codylab.rookie.lessons.domain.Message;
import it.intesys.codylab.rookie.lessons.dto.MessageDto;
import it.intesys.codylab.rookie.lessons.exception.NotFound;
import it.intesys.codylab.rookie.lessons.repository.AccountRepository;
import it.intesys.codylab.rookie.lessons.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ChatRepository chatRepository;

    public Message toDomain  (MessageDto messageDto) {
        if (messageDto == null)
            return null;

        Message message = new Message();
        message.setId(messageDto.getId());
        message.setSender(accountRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new NotFound(messageDto.getSenderId(), Account.class)));
        message.setChat(chatRepository.findById(messageDto.getChatId())
                .orElseThrow(() -> new NotFound(messageDto.getChatId(), Chat.class)));
        message.setText(messageDto.getText());

        return message;
    }

    public MessageDto toDto  (Message message) {
        if (message == null)
            return null;

        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setDateCreated(message.getDateCreated());
        messageDto.setDateModified(message.getDateModified());
        messageDto.setSenderId(message.getSender().getId());
        messageDto.setChatId(message.getChat().getId());
        messageDto.setText(message.getText());

        return messageDto;
    }
}
