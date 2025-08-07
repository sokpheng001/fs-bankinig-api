package kh.edu.cstad.bankingapi.dto;

import lombok.Builder;

@Builder
public record RegisterCustomerReq(
        String fullName,
        String email,
        String password,
        String confirmPassword
) {
}
