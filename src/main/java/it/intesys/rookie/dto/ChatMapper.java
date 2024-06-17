package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.domain.Chat;
import it.intesys.rookie.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChatMapper {
    private final AccountRepository accountRepository;

    public ChatMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Chat toEntity(ChatDTO accountDTO){
        Chat chat = new Chat();
        chat.setId(accountDTO.getId());
        chat.setDateCreated(accountDTO.getDateCreated());
        chat.setDateModified(accountDTO.getDateModified());
        chat.setMembers(accountDTO.getMemberIds().stream().map(accountRepository::findById).map(Optional::get).toList());
        return chat;
    }

    public ChatDTO toDataTransferObject(Chat chat){
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setId(chat.getId());
        chatDTO.setDateCreated(chat.getDateCreated());
        chatDTO.setDateModified(chat.getDateModified());
        chatDTO.setMemberIds(chat.getMembers().stream().map(Account::getId).toList());
        return chatDTO;
    }
}
