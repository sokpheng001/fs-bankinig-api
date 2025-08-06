package kh.edu.cstad.bankingapi;

import kh.edu.cstad.bankingapi.repository.CustomerSegmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FsBankingApiApplicationTests {

    @Autowired
    private CustomerSegmentRepository customerSegmentRepository;

    @Test
    public void testCustomerSegment(){
//        System.out.println(customerSegmentRepository.findAll());

        customerSegmentRepository.findAll()
                .forEach(segment -> System.out.println(segment.getCustomers()));


    }

}
