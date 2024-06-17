package it.intesys.rookie.controller;

import it.intesys.rookie.dto.ChatDTO;
import it.intesys.rookie.service.ChatService;
import it.intesys.rookie.service.NotFound;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ChatApi extends RookieApi{
    public static final String API_CHAT = "/api/chat";
    public static final String API_CHAT_ID = API_CHAT + "/{id}";
    private static final String API_CHAT_FILTER = API_CHAT + "/filter";
    private final ChatService chatService;

    public ChatApi(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/api/chat")
    //get
    ChatDTO createChat(@RequestBody ChatDTO chat){
        return  chatService.createChat (chat);
    }
    @GetMapping(API_CHAT_ID)
    ChatDTO getChat (@PathVariable Long id) {
        ChatDTO chat = chatService.getChat(id);
        return chat;
    }
    //delete
    @DeleteMapping(API_CHAT_ID)
    ResponseEntity<Void> delChat(@PathVariable Long id){
        try {
            chatService.delChat(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
    //update
    @PutMapping(API_CHAT_ID)
    ChatDTO updateChat (@PathVariable Long id, @RequestBody ChatDTO chat) {
        ChatDTO chatDTO = chatService.updateChat(id, chat);
        return chatDTO;

    }
    @PostMapping(API_CHAT_FILTER)
    ResponseEntity <List<ChatDTO>> getChats(@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @RequestBody @Nullable String filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<ChatDTO> chats = chatService.getChats (filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(chats, API_CHAT_FILTER);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(chats.getContent());
    }
    
}
