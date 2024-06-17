package it.intesys.rookie.controller;

import it.intesys.rookie.domain.Account;
import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.service.AccountService;
import it.intesys.rookie.service.Mandatory;
import it.intesys.rookie.service.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class AccountApi extends RookieApi {
    public static final String API_ACCOUNT = "/api/account";
    public static final String API_ACCOUNT_FILTER = API_ACCOUNT + "/filter";
    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(API_ACCOUNT)
    AccountDTO createAccount (@RequestBody AccountDTO account) {
        return accountService.createAccount (account);
    }

    @GetMapping(API_ACCOUNT + "/{id}")
    AccountDTO getAccount (@PathVariable Long id) {
        AccountDTO account = accountService.getAccount(id);
        return account;
    }

    @PutMapping(API_ACCOUNT + "/{id}")
    AccountDTO updateAccount (@PathVariable Long id, @RequestBody AccountDTO account) {
        AccountDTO accountDTO = accountService.updateAccount(id, account);
        return accountDTO;
    }

    @DeleteMapping(API_ACCOUNT + "/{id}")
    ResponseEntity<AccountDTO> deleteAccount (@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("X-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_ACCOUNT_FILTER)
    ResponseEntity<List<AccountDTO>> getAccounts (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam ("sort") String sort, @Nullable @RequestBody String filter) {
        Pageable pageable = pageableOf(page, size, sort);
        Page<AccountDTO> accounts = accountService.getAccounts (filter, pageable);

        HttpHeaders httpHeaders = paginationHeaders(accounts, API_ACCOUNT_FILTER);
        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(accounts.getContent());
    }

}
