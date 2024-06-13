package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Account;
import org.springframework.stereotype.Component;

@Component

public class AccountMapper {
    public Account toEntity(AccountDTO accountDTO){
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setDateCreated(accountDTO.getDateCreated());
        account.setDateModified(accountDTO.getDateModified());
        account.setAlias(accountDTO.getAlias());
        account.setName(accountDTO.getName());
        account.setSurname(accountDTO.getSurname());
        account.setEmail(accountDTO.getEmail());
        return account;

    }

    public AccountDTO toDataTransferObject(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setDateCreated(account.getDateCreated());
        accountDTO.setDateModified(account.getDateModified());
        accountDTO.setName(account.getName());
        accountDTO.setSurname(account.getSurname());
        accountDTO.setAlias(account.getAlias());
        accountDTO.setEmail(account.getEmail());
        return accountDTO;
    }
}
