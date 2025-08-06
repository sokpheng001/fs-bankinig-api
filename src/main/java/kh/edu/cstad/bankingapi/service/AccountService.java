package kh.edu.cstad.bankingapi.service;

import kh.edu.cstad.bankingapi.dto.AccountResponse;
import kh.edu.cstad.bankingapi.dto.CreateAccountRequest;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest createAccountRequest);
    List<AccountResponse> getAllAccounts();
}
