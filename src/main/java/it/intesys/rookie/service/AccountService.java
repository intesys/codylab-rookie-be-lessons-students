package it.intesys.rookie.service;
import it.intesys.rookie.domain.Account;
import it.intesys.rookie.domain.Status;
import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.dto.AccountMapper;
import it.intesys.rookie.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Instant now  = Instant.now();
        account.setDateCreated(now);
        account.setDateModified(now);
        account.setStatus(Status.REGISTERED);

        verify(account);

        account = accountRepository.save(account);
        accountDTO = accountMapper.toDataTransferObject(account);
        return accountDTO;
    }
    public AccountDTO  getAccount(Long id){
        Optional<Account> account = accountRepository.findById(id);
        Optional<AccountDTO> accountDTO = account.map(accountMapper::toDataTransferObject);
        return accountDTO.orElseThrow(() -> new NotFound(Account.class, id));


    }

    public void delAccount(Long id) {
        if (accountRepository.findById (id).isEmpty()) {
            throw new NotFound(Account.class, id);
        }

        accountRepository.deleteAccount(id);
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFound(Account.class, id);
        }

        accountDTO.setId(id);
        Account account = accountMapper.toEntity (accountDTO);
        account.setStatus(existing.get().getStatus());

        Instant now = Instant.now();
        account.setDateModified(now);
        verify(account);

        account = accountRepository.save (account);
        accountDTO = accountMapper.toDataTransferObject (account);
        return accountDTO;
    }

    private void verify(Account account) {
        if (account.getStatus() == null)
            throw new Mandatory(Account.class, account.getId(), "status");
        if (account.getAlias() == null)
            throw new Mandatory(Account.class, account.getId(), "alias");
        if (account.getName() == null)
            throw new Mandatory(Account.class, account.getId(), "name");
        if (account.getSurname() == null)
            throw new Mandatory(Account.class, account.getId(), "surname");
        if (account.getEmail() == null)
            throw new Mandatory(Account.class, account.getId(), "email");

    }

    public Page<AccountDTO> getAccounts(String filter, Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll (filter, pageable);
        return accounts.map(accountMapper::toDataTransferObject);
    }

}
