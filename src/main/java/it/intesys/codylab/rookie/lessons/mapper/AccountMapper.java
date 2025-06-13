package it.intesys.codylab.rookie.lessons.mapper;


import it.intesys.codylab.rookie.lessons.domain.Account;
import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toDomain  (AccountDto accountDto) {
        if (accountDto == null)
            return null;

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setName(accountDto.getName());
        account.setSurname(accountDto.getSurname());
        account.setAlias(accountDto.getAlias());
        account.setEmail(accountDto.getEmail());

        return account;
    }

    public AccountDto toDto  (Account account) {
        if (account == null)
            return null;

        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setName(account.getName());
        accountDto.setSurname(account.getSurname());
        accountDto.setAlias(account.getAlias());
        accountDto.setEmail(account.getEmail());
        accountDto.setDateCreated(account.getDateCreated());
        accountDto.setDateModified(account.getDateModified());

        return accountDto;
    }
}
