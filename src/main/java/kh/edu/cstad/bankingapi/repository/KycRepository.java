package kh.edu.cstad.bankingapi.repository;

import kh.edu.cstad.bankingapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KycRepository  extends JpaRepository<KYC, Integer> {

    boolean existsByNationalCardId (String nationalCardId);

}
