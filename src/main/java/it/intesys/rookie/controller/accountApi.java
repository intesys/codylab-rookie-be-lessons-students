package it.intesys.rookie.controller;

import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.service.AccountService;
import it.intesys.rookie.service.NotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class accountApi {
    public static final String API_ACCOUNT = "/api/account";
    public static final String API_ACCOUNT_ID = API_ACCOUNT + "/{id}";
    private final AccountService accountservice;

    public accountApi(AccountService accountservice) {
        this.accountservice = accountservice;
    }

    @PostMapping(API_ACCOUNT)
    AccountDTO createAccount (@RequestBody AccountDTO account){
        return accountservice.createAccount (account);
    }
    @GetMapping(API_ACCOUNT_ID)
    ResponseEntity<AccountDTO> getAccount (@PathVariable Long id) {
        try{
            AccountDTO account = accountservice.getAccount(id);
         return ResponseEntity.ok(account);
    } catch (NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }

    @PutMapping (API_ACCOUNT_ID)
    AccountDTO updateAccount (@PathVariable Long id, @RequestBody AccountDTO accountdto){
        return accountservice.updateAccount (id, accountdto);
    }

    @DeleteMapping (API_ACCOUNT_ID)
    ResponseEntity<Void> deleteAccount (@PathVariable Long id) {
        try{

        }
    }


}
