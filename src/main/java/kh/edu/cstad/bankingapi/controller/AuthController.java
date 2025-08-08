package kh.edu.cstad.bankingapi.controller;

import kh.edu.cstad.bankingapi.base.BaseResponse;
import kh.edu.cstad.bankingapi.dto.RegisterCustomerReq;

import kh.edu.cstad.bankingapi.dto.ResetPasswordRequest;
import kh.edu.cstad.bankingapi.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;
    @PostMapping("/register")
    public BaseResponse<Object> register(@RequestBody RegisterCustomerReq registerCustomerReq) {
        return BaseResponse.builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .timeStamp(Date.from(Instant.now()))
                .message("Created new customer")
                .data(authService.register(registerCustomerReq))
                .build();
    }
    @PostMapping("/forgot-password/{email}")
    public BaseResponse<Object> forgotPassword(@PathVariable String email){
        return BaseResponse.builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .timeStamp(Date.from(Instant.now()))
                .message("Please check your email to create your new password")
                .data(authService.forgotPassword(email))
                .build();
    }
    @PostMapping("/reset-password")
    public BaseResponse<Object> resetPassword(Authentication authentication, @RequestBody ResetPasswordRequest resetPasswordRequest){
        return BaseResponse.builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .timeStamp(Date.from(Instant.now()))
                .message("Password reset successfully")
                .data(authService.resetPassword(authentication,resetPasswordRequest))
                .build();
    }
}
