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
        account.setDateCreated(Instant.now());
        account.setDateModified(Instant.now());


        account = accountRepository.save (account);
        accountDTO = accountMapper.toDataTrasferObject (account);
        return accountDTO;
    }

    public AccountDTO getAccount (Long id){
        Optional<Account> account = accountRepository.findById (id);
        Optional<AccountDTO> accountDTO = account.map(accountMapper::toDataTrasferObject);
        return accountDTO.orElseThrow(() -> new NotFound(Account.class, id));
    }


    public void deleteAccount (Long id) {
        if(accountRepository.findById (id).isEmpty()){
            throw new NotFound(gyAccount.class, id);
        }
        accountRepository.delete (id);
    }
}
