package it.intesys.rookie.controller;

import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApi {

    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/account")
    AccountDTO createAccount (@RequestBody AccountDTO account) {
        return accountService.createAccount (account);
    }

    @GetMapping("/api/account/{id}")
    ResponseEntity<AccountDTO> getAccount (@PathVariable Long id) {
        try {
            AccountDTO account = accountService.getAccount(id);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }
}
