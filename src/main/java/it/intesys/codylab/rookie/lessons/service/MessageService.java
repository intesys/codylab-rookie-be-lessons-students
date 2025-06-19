package it.intesys.codylab.rookie.lessons.service;

import it.intesys.codylab.rookie.lessons.domain.Message;
import it.intesys.codylab.rookie.lessons.dto.MessageDto;
import it.intesys.codylab.rookie.lessons.dto.MessageFilterDto;
import it.intesys.codylab.rookie.lessons.exception.NotFound;
import it.intesys.codylab.rookie.lessons.exception.SenderNotInChat;
import it.intesys.codylab.rookie.lessons.mapper.MessageMapper;
import it.intesys.codylab.rookie.lessons.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageMapper messageMapper;

    Logger logger = LoggerFactory.getLogger(MessageService.class);

    public MessageDto createMessage(MessageDto messageDto) {
        logger.info("Creating message alias {}", messageDto.getId());
        Message message = messageMapper.toDomain(messageDto);

        if (!message.getChat().getMembers().contains(message.getSender()))
            throw new SenderNotInChat(messageDto.getChatId(), messageDto.getSenderId());

        Instant now = Instant.now();
        message.setDateCreated(now);
        message.setDateModified(now);

        messageRepository.save (message);
        return messageMapper.toDto(message);
    }

    public MessageDto updateMessage(MessageDto messageDto) {
        logger.info("Updating message alias {}", messageDto.getId());
        Optional<Message> messageOptional = messageRepository.findById (messageDto.getId());
        if (messageOptional.isPresent()) {
            Message message = messageMapper.toDomain(messageDto);

            Instant now = Instant.now();
            message.setDateModified(now);

            message = messageRepository.save (message);
            return messageMapper.toDto(message);
        } else {
            throw new NotFound(messageDto.getId(), Message.class);
        }
    }

    public MessageDto getMessage(Long id) {
        Optional<Message> messageOptional = messageRepository.findById (id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            return messageMapper.toDto(message);
        } else {
            throw new NotFound(id, Message.class);
        }
    }

    public void deleteMessage(Long id) {
        Optional<Message> messageOptional = messageRepository.findById (id);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById (id);
        } else {
            throw new NotFound(id, Message.class);
        }
    }

    public List<MessageDto> getMessages(int page, int size, String sort, MessageFilterDto filter) {
        List<Message> messages = messageRepository.findAll (page, size, sort, filter.getText(), filter.getChatId());
        return messages.stream()
                .map(messageMapper::toDto)
                .toList();
    }
}
