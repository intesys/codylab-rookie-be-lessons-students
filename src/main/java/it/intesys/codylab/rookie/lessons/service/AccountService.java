package it.intesys.codylab.rookie.lessons.service;

import it.intesys.codylab.rookie.lessons.api.AccountApi;
import it.intesys.codylab.rookie.lessons.domain.Account;
import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.exception.NotFound;
import it.intesys.codylab.rookie.lessons.mapper.AccountMapper;
import it.intesys.codylab.rookie.lessons.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;
    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountDto createAccount(AccountDto accountDto) {
        logger.info("Creating account alias {}", accountDto.getAlias());

        Account account = accountMapper.toDomain(accountDto);
        Instant now = Instant.now();
        account.setDateCreated(now);
        account.setDateModified(now);


        accountRepository.save(account);
        return accountMapper.toDto(account);

    }

    public AccountDto updateAccount(AccountDto accountDto) {
        logger.info("Updating account alias {}", accountDto.getAlias());
        Optional<Account> accountOptional = accountRepository.findById(accountDto.getId());
        if (accountOptional.isPresent()) {
            Account account = accountMapper.toDomain(accountDto);

            Instant now = Instant.now();
            account.setDateModified(now);

            account = accountRepository.save(account);
            return accountMapper.toDto(account);
        } else {
            throw new NotFound(accountDto.getId(), Account.class);
        }
    }

    public AccountDto getAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return accountMapper.toDto(account);
        } else {
            throw new NotFound(id, Account.class);
        }
    }

    public void deleteAccount(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            accountRepository.deleteById(id);
        } else {
            throw new NotFound(id, Account.class);
        }
    }
    public List<AccountDto> getAccounts(int page, int size, String sort, String filter) {
        List <Account> accounts = accountRepository.findAll(page, size, sort, filter);
        return accounts.stream()
                .map(accountMapper:: toDto)
                .toList();

    }
}



