package it.intesys.rookie.service;
import it.intesys.rookie.domain.Chat;
import it.intesys.rookie.domain.Status;
import it.intesys.rookie.dto.ChatDTO;
import it.intesys.rookie.dto.ChatMapper;
import it.intesys.rookie.repository.ChatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;

    public ChatService(ChatRepository chatRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    public ChatDTO createChat(ChatDTO chatDTO) {
        Chat chat = chatMapper.toEntity (chatDTO);

        Instant now  = Instant.now();
        chat.setDateCreated(now);
        chat.setDateModified(now);

        verify(chat);

        chat = chatRepository.save(chat);
        chatDTO = chatMapper.toDataTransferObject(chat);
        return chatDTO;
    }
    public ChatDTO  getChat(Long id){
        Optional<Chat> chat = chatRepository.findById(id);
        Optional<ChatDTO> chatDTO = chat.map(chatMapper::toDataTransferObject);
        return chatDTO.orElseThrow(() -> new NotFound(Chat.class, id));


    }

    public void delChat(Long id) {
        if (chatRepository.findById (id).isEmpty()) {
            throw new NotFound(Chat.class, id);
        }

        chatRepository.deleteChat(id);
    }

    public ChatDTO updateChat(Long id, ChatDTO chatDTO) {
        Optional<Chat> existing = chatRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFound(Chat.class, id);
        }

        chatDTO.setId(id);
        Chat chat = chatMapper.toEntity (chatDTO);

        Instant now = Instant.now();
        chat.setDateModified(now);
        verify(chat);

        chat = chatRepository.save (chat);
        chatDTO = chatMapper.toDataTransferObject (chat);
        return chatDTO;
    }

    private void verify(Chat chat) {

    }

    public Page<ChatDTO> getChats(String filter, Pageable pageable) {
        Page<Chat> chats = chatRepository.findAll (filter, pageable);
        return chats.map(chatMapper::toDataTransferObject);
    }

}
