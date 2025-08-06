package kh.edu.cstad.bankingapi.dto;

import java.math.BigDecimal;

public record CreateAccountRequest(

        String accountNumber,
        String accountName,
        BigDecimal balance,
        String currency,
        String accountType,
        String phoneNumber
) {
}
