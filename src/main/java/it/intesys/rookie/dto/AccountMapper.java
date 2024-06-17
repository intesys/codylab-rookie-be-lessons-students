package it.intesys.rookie.dto;
import java.time.Instant;
import java.util.Optional;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.domain.Status;
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
        account.setStatus(Optional.ofNullable(accountDTO.getStatus())
            .map(s -> Status.valueOf(s.name()))
            .orElse(null));
        return account;
    }

    public AccountDTO toDataTransferObject(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setDateCreated(account.getDateCreated());
        accountDTO.setDateModified(account.getDateModified());
        accountDTO.setAlias(account.getAlias());
        accountDTO.setName(account.getName());
        accountDTO.setSurname(account.getSurname());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setStatus(StatusDTO.valueOf(account.getStatus().name()));
        return accountDTO;
    }
}
