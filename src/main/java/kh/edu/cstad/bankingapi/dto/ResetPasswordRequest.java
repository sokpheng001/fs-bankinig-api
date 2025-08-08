package kh.edu.cstad.bankingapi.dto;

import lombok.Builder;

@Builder
public record ResetPasswordRequest(
        String email,
        String newPassword,
        String newConfirmPassword
) {
}
