package kh.edu.cstad.bankingapi.service;

import kh.edu.cstad.bankingapi.dto.CustomerResponse;
import kh.edu.cstad.bankingapi.dto.RegisterCustomerReq;
import kh.edu.cstad.bankingapi.dto.ResetPasswordRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


public interface AuthService {
    CustomerResponse register(RegisterCustomerReq registerCustomerReq);
    CustomerResponse resetPassword(Authentication authentication, ResetPasswordRequest resetPasswordRequest);
    CustomerResponse forgotPassword(String email);
}
