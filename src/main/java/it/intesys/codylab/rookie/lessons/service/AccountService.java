package it.intesys.codylab.rookie.lessons.service;

import it.intesys.codylab.rookie.lessons.api.AccountApi;
import it.intesys.codylab.rookie.lessons.domain.Account;
import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.mapper.AccountMapper;
import it.intesys.codylab.rookie.lessons.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountDto createAccount(AccountDto accountDto) {
        logger.info("Creating account alias{}", accountDto.getAlias());
        Account account = accountMapper.toDomain(accountDto);
        Instant now = Instant.now();
        account.setDateCreated(now);
        account.setDateModified(now);
        accountRepository.save(account);
        accountDto = accountMapper.toDto(account);
        return accountDto;
    }
}
