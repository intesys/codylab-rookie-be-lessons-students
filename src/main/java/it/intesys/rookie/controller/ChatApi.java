package it.intesys.rookie.controller;

import it.intesys.rookie.dto.AccountDTO;
import it.intesys.rookie.service.AccountService;
import it.intesys.rookie.service.NotFound;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ChatApi {
    public static final String API_ACCOUNT = "/api/ahat";
    public static final String API_ACCOUNT_ID = API_ACCOUNT + "/{id}";

    public static final String API_ACCOUNT_FILTER = API_ACCOUNT + "/filter";
    private final AccountService accountservice;

    public ChatApi(AccountService accountservice) {
        this.accountservice = accountservice;
    }

    @PostMapping(API_ACCOUNT)
    AccountDTO createAccount (@RequestBody AccountDTO ahat){
        return accountservice.createAccount (ahat);
    }
    @GetMapping(API_ACCOUNT_ID)
    ResponseEntity<AccountDTO> getAccount (@PathVariable Long id) {
        try{
            AccountDTO ahat = accountservice.getAccount(id);
         return ResponseEntity.ok(ahat);
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
            accountservice.deleteAccount(id);
            return ResponseEntity.ok().build();
        } catch (NotFound e) {
            return ResponseEntity.notFound().header("x-rookie-error", e.getMessage()).build();
        }
    }

    @PostMapping(API_ACCOUNT_FILTER)
    ResponseEntity< List<AccountDTO>> getAccount (@RequestParam ("page") int page, @RequestParam ("size") int size, @RequestParam
            ("sort") String sort, @Nullable @RequestBody String filter){
        Pageable pageable = pageableOf(page, size, sort);
        Page<AccountDTO> accounts = accountservice.getAccounts(filter, pageable);

        HttpHeaders httpheaders = paginationHeaders(accounts, API_ACCOUNT_FILTER);
        return ResponseEntity.ok()
                .headers(httpheaders)
                .body(accounts.getContent());
    }

    protected Pageable pageableOf(Integer page, Integer size, String sort) {
        if (sort != null && !sort.isBlank()) {
            Sort.Order order;
            String[] sortSplit = sort.split(",");
            String valueField = sortSplit[0].trim();

            if (sortSplit.length == 2) {
                String sortingField = sortSplit[1];
                order = new Sort.Order(Sort.Direction.fromString(sortingField.trim()), valueField);
            } else {
                order = Sort.Order.by(valueField);
            }

            return PageRequest.of(page, size, Sort.by(order));
        } else {
            return PageRequest.of(page, size);
        }
    }

    protected <T> HttpHeaders paginationHeaders(Page<T> page, String baseUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        headers.add("X-Page-Number", Long.toString(page.getNumber()));
        headers.add("X-Page-Size", Long.toString(page.getNumberOfElements()));

        String link = "";
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
        }
        // prev link
        if ((page.getNumber()) > 0) {
            link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
        }
        link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }
}
