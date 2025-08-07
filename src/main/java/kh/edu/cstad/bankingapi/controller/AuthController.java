package kh.edu.cstad.bankingapi.controller;

import kh.edu.cstad.bankingapi.base.BaseResponse;
import kh.edu.cstad.bankingapi.dto.RegisterCustomerReq;
import kh.edu.cstad.bankingapi.service.AuthService;
import kh.edu.cstad.bankingapi.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;
    @PostMapping("/register")
    public BaseResponse<Object> register(@RequestBody RegisterCustomerReq registerCustomerReq){
        return BaseResponse.builder()
                .status(String.valueOf(HttpStatus.CREATED.value()))
                .timeStamp(Date.from(Instant.now()))
                .message("Created new customer")
                .data(authService.register(registerCustomerReq))
                .build();
    }
}
