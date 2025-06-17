package it.intesys.codylab.rookie.lessons.service;

import it.intesys.codylab.rookie.lessons.domain.Chat;
import it.intesys.codylab.rookie.lessons.dto.ChatDto;
import it.intesys.codylab.rookie.lessons.exception.NotFound;
import it.intesys.codylab.rookie.lessons.mapper.ChatMapper;
import it.intesys.codylab.rookie.lessons.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatMapper chatMapper;

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    public ChatDto createChat(ChatDto chatDto) {
        logger.info("Creating chat");
        Chat chat = chatMapper.toDomain(chatDto);

        Instant now = Instant.now();
        chat.setDateCreated(now);
        chat.setDateModified(now);

        chatRepository.save (chat);
        return chatMapper.toDto(chat);
    }

    public ChatDto updateChat(ChatDto chatDto) {
        logger.info("Updating chat with id {}", chatDto.getId());
        Optional<Chat> chatOptional = chatRepository.findById (chatDto.getId());
        if (chatOptional.isPresent()) {
            Chat chat = chatMapper.toDomain(chatDto);

            Instant now = Instant.now();
            chat.setDateModified(now);

            chat = chatRepository.save (chat);
            return chatMapper.toDto(chat);
        } else {
            throw new NotFound(chatDto.getId(), Chat.class);
        }
    }

    public ChatDto getChat(Long id) {
        Optional<Chat> chatOptional = chatRepository.findById (id);
        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            return chatMapper.toDto(chat);
        } else {
            throw new NotFound(id, Chat.class);
        }
    }

    public void deleteChat(Long id) {
        chatRepository.deleteById (id);
        Optional<Chat> chatOptional = chatRepository.findById (id);
        if (chatOptional.isPresent()) {
            chatRepository.deleteById (id);
        } else {
            throw new NotFound(id, Chat.class);
        }
    }
}
