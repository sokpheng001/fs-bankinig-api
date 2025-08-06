package kh.edu.cstad.bankingapi.mapper;

import kh.edu.cstad.bankingapi.domain.Customer;
import kh.edu.cstad.bankingapi.dto.CreateCustomerRequest;
import kh.edu.cstad.bankingapi.dto.CustomerResponse;
import kh.edu.cstad.bankingapi.dto.UpdateCustomerRequest;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    default Page<CustomerResponse> mapFromPageCustomerToPageCustomerResponse(Page<Customer> page){
        List<CustomerResponse> customers = page.getContent()
                .stream()
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(customers, page.getPageable(), page.getTotalElements());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartial (UpdateCustomerRequest request,@MappingTarget Customer customer);

//    Domain -> DTO
    // 1 . return type
    // 2. parameter
    CustomerResponse toCustomerResponse(Customer customer);

//    List<Domain> -> List<DTO>
    List<CustomerResponse> toCustomerResponseList(List<Customer> customers);

//    DTO -> Domain
    @Mapping(source = "customerSegment", target = "customerSegment.segment")
    Customer toCustomer(CreateCustomerRequest request);

}
