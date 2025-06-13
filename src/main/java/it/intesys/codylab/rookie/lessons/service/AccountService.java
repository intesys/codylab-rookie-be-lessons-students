package it.intesys.codylab.roockie.lessons.service;

import it.intesys.codylab.roockie.lessons.domain.Account;
import it.intesys.codylab.roockie.lessons.dto.AccountDto;
import it.intesys.codylab.roockie.lessons.map.AccountMapper;
import it.intesys.codylab.roockie.lessons.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountMapper accountMapper;

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.toDomain(accountDto);
        accountRepository.save (account);
        return accountMapper.toDto(account);

    }
}
