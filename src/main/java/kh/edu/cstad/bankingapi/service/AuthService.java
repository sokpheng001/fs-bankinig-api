package kh.edu.cstad.bankingapi.service;

import kh.edu.cstad.bankingapi.dto.CustomerResponse;
import kh.edu.cstad.bankingapi.dto.RegisterCustomerReq;
import org.springframework.stereotype.Service;


public interface AuthService {
    CustomerResponse register(RegisterCustomerReq registerCustomerReq);
}
