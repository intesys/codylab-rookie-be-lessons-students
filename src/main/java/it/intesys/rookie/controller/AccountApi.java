package it.intesys.rookie.controller;

import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.service.AccountService;
import it.intesys.rookie.service.NotFound;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountApi extends Controller {
    public static final String API_ACCOUNT = "/api/account";
    public static final String API_ACCOUNT_ID = API_ACCOUNT + "/{id}";
    public static final String API_ACCOUNT_FILTER = API_ACCOUNT + "/filter";
    private final AccountService accountService;

    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(API_ACCOUNT)
    AccountDTO createAccount(@RequestBody AccountDTO account) {
        return accountService.createAccount(account);
    }

    @GetMapping(API_ACCOUNT_ID)
    ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        try {
            AccountDTO account = accountService.getAccount(id);
            return ResponseEntity.ok(account);
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }

    @PutMapping(API_ACCOUNT_ID)
    public AccountDTO updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccount(id, accountDTO);
    }

    @DeleteMapping(API_ACCOUNT_ID)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_ACCOUNT_FILTER)
    ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestBody @Nullable String filter) {
        Pageable pageable = pageableOf(page, size, sort);
        Page<AccountDTO> accounts = accountService.getAccounts(filter, pageable);
        HttpHeaders httpHeaders = paginationHeaders(accounts, API_ACCOUNT_FILTER);
        return ResponseEntity.ok().headers(httpHeaders).body(accounts.getContent());
    }
}




