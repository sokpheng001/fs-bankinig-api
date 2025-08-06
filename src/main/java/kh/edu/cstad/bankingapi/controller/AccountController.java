package kh.edu.cstad.bankingapi.controller;

import kh.edu.cstad.bankingapi.base.BaseResponse;
import kh.edu.cstad.bankingapi.domain.Account;
import kh.edu.cstad.bankingapi.dto.AccountResponse;
import kh.edu.cstad.bankingapi.dto.CreateAccountRequest;
import kh.edu.cstad.bankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return accountService.createAccount(createAccountRequest);
    }
    @GetMapping
    public BaseResponse<Object> getAllAccounts(){
        return BaseResponse.builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .timeStamp(Date.from(Instant.now()))
                .message("Get All Accounts")
                .data(accountService.getAllAccounts())
                .build();
    }
}
