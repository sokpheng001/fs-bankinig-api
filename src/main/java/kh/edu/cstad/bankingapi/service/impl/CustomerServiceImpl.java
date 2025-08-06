package kh.edu.cstad.bankingapi.service.impl;

import kh.edu.cstad.bankingapi.domain.Customer;
import kh.edu.cstad.bankingapi.domain.CustomerSegment;
import kh.edu.cstad.bankingapi.domain.KYC;
import kh.edu.cstad.bankingapi.dto.CreateCustomerRequest;
import kh.edu.cstad.bankingapi.dto.CustomerResponse;
import kh.edu.cstad.bankingapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.bankingapi.mapper.CustomerMapper;
import kh.edu.cstad.bankingapi.repository.CustomerRepository;
import kh.edu.cstad.bankingapi.repository.CustomerSegmentRepository;
import kh.edu.cstad.bankingapi.repository.KycRepository;
import kh.edu.cstad.bankingapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerSegmentRepository customerSegmentRepository;
    private final KycRepository kycRepository;

    @Override
    public List<CustomerResponse> findAllCustomer() {
        List<Customer> customers = customerRepository.findAllByIsDeletedFalse(

        );

        // logic find all
        return customerMapper.toCustomerResponseList(customers);
    }

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {

        if(customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if(customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }

        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);

//        Validate customer segment
        CustomerSegment customerSegment = customerSegmentRepository
                .findBySegment(createCustomerRequest.customerSegment())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer segment not found"));

        //set customer segment
        customer.setCustomerSegment(customerSegment);

        // validate KYC
        if(kycRepository.existsByNationalCardId(createCustomerRequest.nationalCardId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National card already exists");
        }

        // create kyc for customer
        KYC kyc = new KYC();
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setCustomer(customer);

        //set kyc to customer
        customer.setKyc(kyc);
        customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerByEmail(String email, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found!!"));

        customerMapper.toCustomerPartial(updateCustomerRequest, customer);

        customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public void deleteCustomerByUuid(String uuid) {

        Customer customer = customerRepository.findCustomerByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer not found!!"
                        ));

        customerRepository.delete(customer);
    }

    // Use Transactional to manage all transaction by developer cuz we use JPQL not JPA
    // In database transaction can be save, update, delete
    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {

        if (!customerRepository.isExistByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number not found");
        }

        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public Page<CustomerResponse> getAllCustomerByPagination(int pageNumber, int pageSize) {
        Page<Customer> customers = customerRepository
                .findAll(PageRequest
                        .of(pageNumber,
                                pageSize,
                                Sort.Direction.DESC
                                ,"fullName"));
        return customerMapper.mapFromPageCustomerToPageCustomerResponse(
                customers
        );
    }
}
