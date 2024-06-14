package it.intesys.rookie.dto;

import it.intesys.rookie.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toEntity(AccountDTO accountDTO){
        Account account  = new Account();
        account.setId(accountDTO.getId());
        account.setDataCreated(accountDTO.getDataCreated());
        account.setDataModified(accountDTO.getDataModified());
        account.setAlias(accountDTO.getAlias());
        account.setName(accountDTO.getName());
        account.setSurname(accountDTO.getSurname());
        account.setEmail(accountDTO.getEmail());
        return account;
    }

    public AccountDTO toDataTransferObject(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setDataCreated(account.getDataCreated());
        accountDTO.setDataModified(account.getDataModified());
        accountDTO.setAlias(account.getAlias());
        accountDTO.setName(account.getName());
        accountDTO.setSurname(account.getSurname());
        accountDTO.setEmail(account.getEmail());
        return accountDTO;

    }
}
