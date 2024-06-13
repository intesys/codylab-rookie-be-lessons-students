package it.intesys.rookie.controller;

import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.dto.AccountMapper;
import it.intesys.rookie.service.AccountService;
import it.intesys.rookie.service.NotFound;
import org.springframework.data.relational.core.sql.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApi {
    public static final String API_ACCOUNT = "/api/account";
    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(API_ACCOUNT)
    AccountDTO createAccount (@RequestBody AccountDTO account) {
        return accountService.createAccount(account);
    }

    @GetMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<AccountDTO> getAccount (@PathVariable Long id) {
        try {
            AccountDTO account = accountService.getAccount(id);
            return ResponseEntity.ok(account);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PutMapping(API_ACCOUNT + "/{id}")
    AccountDTO updateAccount (@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccount(id, accountDTO);
    }

    @DeleteMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<AccountDTO> deleteAccount (@PathVariable Long id) {
        try {
            AccountDTO account = accountService.deleteAccount(id);
            return ResponseEntity.ok(account);
        } catch (NotFound e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
}
