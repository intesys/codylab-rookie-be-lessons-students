package it.intesys.codylab.roockie.lessons.api;

import it.intesys.codylab.roockie.lessons.dto.AccountDto;
import it.intesys.codylab.roockie.lessons.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountApi {
    @Autowired
    AccountService accountService;

    @PostMapping("/api/account")
    AccountDto createAccount(@RequestBody AccountDto accountDto){
         return accountService.createAccount(accountDto);


    }
}
