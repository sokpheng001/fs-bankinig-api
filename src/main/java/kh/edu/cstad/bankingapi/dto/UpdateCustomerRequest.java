package kh.edu.cstad.bankingapi.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender
) { }
