package it.intesys.rookie.service;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.dto.AccountMapper;
import it.intesys.rookie.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity (accountDTO);

        Instant now = Instant.now();
        account.setDateCreated(now);
        account.setDateModified(now);

        account = accountRepository.save (account);
        accountDTO = accountMapper.toDataTransferObject (account);
        return accountDTO;
    }

    public AccountDTO getAccount(Long id) {
        Optional<Account> account = accountRepository.findById (id);
        Optional<AccountDTO> accountDTO = account.map(accountMapper::toDataTransferObject);
        return accountDTO.orElseThrow(() -> new NotFound(Account.class, id));
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        if (accountRepository.findById (id).isEmpty()) {
            throw new NotFound(Account.class, id);
        }

        accountDTO.setId(id);
        Account account = accountMapper.toEntity (accountDTO);

        Instant now = Instant.now();
        account.setDateModified(now);

        account = accountRepository.save (account);
        accountDTO = accountMapper.toDataTransferObject (account);
        return accountDTO;
    }

    public void deleteAccount(Long id) {
        if (accountRepository.findById (id).isEmpty()) {
            throw new NotFound(Account.class, id);

        }

        accountRepository.delete(id);
    }
}
