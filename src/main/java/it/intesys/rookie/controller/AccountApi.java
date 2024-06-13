package it.intesys.rookie.controller;

import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.service.AccountService;
import it.intesys.rookie.service.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApi {
    public static final String API_ACCOUNT = "/api/account";
    public static final String API_ACCOUNT_ID = API_ACCOUNT + "/{id}";
    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/account")

    AccountDTO createAccount(@RequestBody AccountDTO account){
        return  accountService.createAccount (account);
    }
    @GetMapping(API_ACCOUNT_ID)
    ResponseEntity <AccountDTO> getAccount(@PathVariable Long id){
        try{
            AccountDTO account = accountService.getAccount(id);
            return ResponseEntity.ok(account);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @DeleteMapping(API_ACCOUNT_ID)
    ResponseEntity<AccountDTO> delAccount(@PathVariable Long id){
        try{
            AccountDTO account = accountService.delAccount(id);
            return ResponseEntity.ok(account);
        }catch(NotFound e){
            return ResponseEntity.notFound().header("X-rookie-header",e.getMessage()).build();
        }
    }
    @PutMapping(API_ACCOUNT_ID)
    AccountDTO updateAccount (@PathVariable Long id, @RequestBody AccountDTO accountDTO){
        return accountService.updateAccount(id, accountDTO);
    }


}
