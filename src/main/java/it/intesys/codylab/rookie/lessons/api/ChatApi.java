package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.ChatDto;
import it.intesys.codylab.rookie.lessons.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatApi {
    public static final String CHAT_PATH = "/api/chat";
    @Autowired
    ChatService chatService;

    Logger logger = LoggerFactory.getLogger(ChatApi.class);

    @PostMapping(CHAT_PATH)
    ChatDto createChat (@RequestBody ChatDto chatDto) {
        logger.info("Creating chat");
        return chatService.createChat(chatDto);
    }

    @PutMapping(CHAT_PATH + "/{id}")
    ChatDto updateChat (@PathVariable("id") Long id, @RequestBody ChatDto chatDto) {
        chatDto.setId(id);
        return chatService.updateChat(chatDto);
    }

    @GetMapping(CHAT_PATH + "/{id}")
    ChatDto getChat (@PathVariable("id") Long id) {
        return chatService.getChat(id);
    }

    @DeleteMapping(CHAT_PATH + "/{id}")
    void deleteChat (@PathVariable("id") Long id) {
        chatService.deleteChat(id);
    }
}
