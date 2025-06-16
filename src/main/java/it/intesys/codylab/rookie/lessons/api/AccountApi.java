package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountApi {
    private static final String ACCOUNT_PATH = "/api/account" ;
    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountApi.class);
    @PostMapping("/api/account")
    AccountDto createAccount(@RequestBody AccountDto accountDto){
        logger.info("Creating account alias {}", accountDto.getAlias());
        return accountService.createAccount(accountDto);
    }

    @PutMapping (ACCOUNT_PATH + "/{id}")
    AccountDto updateAccount(@PathVariable("id") Long id, @RequestBody AccountDto accountDto){
        logger.info("Updating account alias {}", id); accountDto.getAlias();
        accountDto.setId(id);
        return accountService.updateAccount(accountDto);
    }

    @GetMapping (ACCOUNT_PATH + "/{id}")
    AccountDto getAccount(@PathVariable("id") Long id){
        return accountService.getAccount(id);
    }

    @DeleteMapping (ACCOUNT_PATH + "/{id}")
    void deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
    }


}
