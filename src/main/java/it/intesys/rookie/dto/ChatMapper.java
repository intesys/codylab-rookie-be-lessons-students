package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.domain.Chat;
import it.intesys.rookie.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    private final AccountRepository accountRepository;

    public ChatMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account toEntity(ChattDTO chatDTO) {
        Chat chat = new Chat();
        chat.setId(accountDTO.getId());
        chat.setDateCreated(accountDTO.getDateCreated());
        chat.setDateModified(account.getDateModified());
        return chat;
    }

    public AccountDTO toDataTrasferObject(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setDateCreated(account.getDateCreated());
        accountDTO.setDateModified(account.getDateModified());
        accountDTO.setName(account.getName());
        accountDTO.setSurname(account.getSurname());
        accountDTO.setEmail(account.getEmail());
        return accountDTO;

    }
}
