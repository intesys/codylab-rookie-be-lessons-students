package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.dto.ChatDto;
import it.intesys.codylab.rookie.lessons.dto.ChatFilterDto;
import it.intesys.codylab.rookie.lessons.service.ChatService;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatApi extends RookieApi {
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
    @PostMapping(CHAT_PATH + "/filter")
    List<ChatDto> getChats (@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                  @RequestParam("sort") String sort, @Nullable @RequestBody ChatFilterDto filter) {
        if (page == null)
            page = 0;

        if (size == null)
            size = DEFAULT_SIZE;

        if (sort == null || sort.isBlank())
            sort = "alias";

        return chatService.getChats (page, size, sort, filter);
    }
}
