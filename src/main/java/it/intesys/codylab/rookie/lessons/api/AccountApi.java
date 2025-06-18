package it.intesys.codylab.rookie.lessons.api;

import it.intesys.codylab.rookie.lessons.dto.AccountDto;
import it.intesys.codylab.rookie.lessons.service.AccountService;
import it.intesys.codylab.rookie.lessons.exception.NotFound;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountApi extends RookieApi{
    public static final String PATH = "/api/account";
    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountApi.class);

    @PostMapping(PATH)
    AccountDto createAccount(@RequestBody AccountDto accountDto){
        logger.info("Creating account alias{}", accountDto.getAlias());
        return accountService.createAccount(accountDto);
    }

    @PutMapping(PATH + "/{id}")
    AccountDto updateAccount(@PathVariable("id") Long id, @RequestBody AccountDto accountDto) throws NotFound {
        accountDto.setId(id);
        return accountService.updateAccount(accountDto);
    }

    @GetMapping(PATH + "/{id}")
    AccountDto getAccount(@PathVariable("id") Long id) throws NotFound {
        return accountService.getAccount(id);
    }

    @DeleteMapping(PATH + "/{id}")
    void deleteAccount(@PathVariable("id") Long id) throws NotFound {
        accountService.deleteAccount(id);
    }

    @PostMapping(PATH +"/filter")
    List<AccountDto> getAccounts(@RequestParam("page")Integer page, @RequestParam("size") Integer size, @RequestParam("sort") String sort, @Nullable @RequestBody String filter){

        if(page == null)
            page = 0;

        if(size == null)
            size = DEAFULT_SIZE;

        if(sort == null || sort.isBlank())
            sort = "alias";
        return accountService.getAccounts(page, size, sort, filter);
    }
}
