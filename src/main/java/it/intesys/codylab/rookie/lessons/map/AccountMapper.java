package it.intesys.codylab.roockie.lessons.map;

import it.intesys.codylab.roockie.lessons.domain.Account;
import it.intesys.codylab.roockie.lessons.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toDomain (AccountDto accountDto) {
        if (accountDto == null)
            return null;

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setName(accountDto.getName());
        account.setSurname(accountDto.getSurname());
        account.setEmail(accountDto.getEmail());
        account.setDateCreated(accountDto.getDateCreated());
        account.setDateModified(accountDto.getDateModified());

        return account;

    }
    public AccountDto toDto (Account account) {
        if (account == null)
            return null;


        AccountDto accountDto = new AccountDto();
        account.setId(account.getId());
        account.setName(account.getName());
        account.setSurname(account.getSurname());
        account.setEmail(account.getEmail());
        account.setDateCreated(account.getDateCreated());
        account.setDateModified(account.getDateModified());

        return accountDto;



    }

}
