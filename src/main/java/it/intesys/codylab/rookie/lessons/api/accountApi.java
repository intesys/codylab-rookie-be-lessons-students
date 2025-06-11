package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class accountApi {
    @Autowired
    AccountService accountService;
    void createAccount(@RequestBody AccountDto accountDto){
        accountService.createAccount(accountDto);

    }
}
