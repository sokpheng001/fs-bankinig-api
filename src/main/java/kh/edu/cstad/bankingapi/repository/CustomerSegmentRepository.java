package kh.edu.cstad.bankingapi.repository;

import kh.edu.cstad.bankingapi.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Integer> {

    Optional<CustomerSegment> findBySegment(String segment);

}
