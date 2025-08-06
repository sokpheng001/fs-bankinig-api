package kh.edu.cstad.bankingapi.service;


import kh.edu.cstad.bankingapi.dto.CreateCustomerRequest;
import kh.edu.cstad.bankingapi.dto.CustomerResponse;
import kh.edu.cstad.bankingapi.dto.UpdateCustomerRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    /**
     * Find All Customers
     * @return List of Customer Response
     */
    List<CustomerResponse> findAllCustomer();


    /**
     * Create new customer
     * @param createCustomerRequest
     * @return createCustomerRequest
     */
    CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);

    /**
     * Update partially of field Full name and gender
     * @param email
     * @param updateCustomerRequest
     * @return CustomerResponse
     */
    CustomerResponse updateCustomerByEmail(String email, UpdateCustomerRequest updateCustomerRequest);

    void deleteCustomerByUuid(String uuid);

    void disableByPhoneNumber(String phoneNumber);
    /**
     * Find all customers by pagination
     */
    Page<CustomerResponse> getAllCustomerByPagination(int pageNumber, int pageSize);

}
