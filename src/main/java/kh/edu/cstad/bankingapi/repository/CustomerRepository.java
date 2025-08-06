package kh.edu.cstad.bankingapi.repository;

import kh.edu.cstad.bankingapi.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String>
        ,JpaRepository<Customer, String> {

    List<Customer> findAllByIsDeletedFalse();

    @Modifying
    @Query("""
            UPDATE Customer c SET c.isDeleted = TRUE WHERE c.phoneNumber = :phoneNumber
            """)
    void disableByPhoneNumber(String phoneNumber);

    @Query(""" 
            SELECT count (c) > 0 from Customer c where c.phoneNumber = :phoneNumber
            """)
   boolean isExistByPhoneNumber(String phoneNumber);

    List<Customer> findAll();

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findCustomerByUuid(String uuid);

}
