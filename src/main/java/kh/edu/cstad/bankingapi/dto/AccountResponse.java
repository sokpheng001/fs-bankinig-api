package kh.edu.cstad.bankingapi.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String accountNumber,
        String accountName,
        String currency,
        BigDecimal balance
) {
}
