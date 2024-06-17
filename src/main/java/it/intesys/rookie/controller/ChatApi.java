package it.intesys.rookie.controller;

import it.intesys.rookie.dto.ChatDTO;
import it.intesys.rookie.service.ChatService;
import it.intesys.rookie.service.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ChatApi extends RookieApi{
    public static final String API_ACCOUNT = "/api/chat";
    public static final String API_ACCOUNT_FILTER = API_ACCOUNT + "/filter";
    private final ChatService chatService;

    public ChatApi(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(API_ACCOUNT)
    ChatDTO createChat (@RequestBody ChatDTO chat) {
        return chatService.createChat (chat);
    }

    @GetMapping(API_ACCOUNT + "/{id}")
    ChatDTO getChat (@PathVariable Long id) {
        ChatDTO chat = chatService.getChat(id);
        return chat;
    }

    @PutMapping(API_ACCOUNT + "/{id}")
    ChatDTO updateChat (@PathVariable Long id, @RequestBody ChatDTO chat) {
        ChatDTO chatDTO = chatService.updateChat(id, chat);
        return chatDTO;
    }

    @DeleteMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<ChatDTO> deleteChat (@PathVariable Long id) {
        try {
            chatService.deleteChat(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_ACCOUNT_FILTER)
    ResponseEntity<List<ChatDTO>> getChats (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody String filter) {
        Pageable pageable = pageableOf(page, size, sort);
        Page<ChatDTO> chats = chatService.getChats (filter, pageable);

        HttpHeaders httpHeaders = paginationHeaders(chats, API_ACCOUNT_FILTER);
        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(chats.getContent());
    }

}
