package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountApi {
    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountApi.class);

    @PostMapping("/api/account")
    AccountDto createAccount(@RequestBody AccountDto accountDto){
        logger.info("Creating account alias{}", accountDto.getAlias());
        return accountService.createAccount(accountDto);
    }
}
