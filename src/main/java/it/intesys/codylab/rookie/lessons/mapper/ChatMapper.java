package it.intesys.codylab.rookie.lessons.mapper;

import it.intesys.codylab.rookie.lessons.domain.Account;
import it.intesys.codylab.rookie.lessons.domain.Chat;
import it.intesys.codylab.rookie.lessons.dto.ChatDto;
import it.intesys.codylab.rookie.lessons.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ChatMapper {
    @Autowired
    AccountRepository accountRepository;

    public Chat toDomain(ChatDto chatDto){
        if(chatDto == null)
            return null;

        Chat chat = new Chat();
        chat.setId(chatDto.getId());
        chat.setMembers(chatDto.getMemberIds()
                .stream()
                .map(accountRepository::findById)
                .map(Optional::orElseThrow)
                .toList());
        return chat;
    }

    public ChatDto toDto(Chat chat){

        if (chat == null)
            return null;

        ChatDto chatDto = new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setDateCreated(chat.getDateCreated());
        chatDto.setDateModified(chat.getDateModified());

        chatDto.setMemberIds(chat.getMembers().stream()
                .map(Account::getId)
                .toList());
        return chatDto;
    }
}
