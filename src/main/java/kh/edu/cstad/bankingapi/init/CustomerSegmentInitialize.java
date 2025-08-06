package kh.edu.cstad.bankingapi.init;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.bankingapi.domain.CustomerSegment;
import kh.edu.cstad.bankingapi.repository.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitialize {

    private final CustomerSegmentRepository customerSegmentRepository;

    @PostConstruct
    public void init() {
        if(customerSegmentRepository.count() == 0){
            CustomerSegment customerSegment = new CustomerSegment();

            customerSegment.setSegment("REGULAR");
            customerSegment.setDescription("Normal Customer");
            customerSegment.setIsDeleted(false);

            CustomerSegment customerSegmentSilver = new CustomerSegment();

            customerSegmentSilver.setSegment("SILVER");
            customerSegmentSilver.setDescription("Silver Customer");
            customerSegmentSilver.setIsDeleted(false);

            customerSegmentRepository.save(customerSegment);
            customerSegmentRepository.save(customerSegmentSilver);

            customerSegmentRepository.save(customerSegment);
            customerSegmentRepository.save(customerSegmentSilver);
        }
    }

}
