package kh.edu.cstad.bankingapi.init;

import jakarta.annotation.PostConstruct;
import kh.edu.cstad.bankingapi.domain.AccountType;
import kh.edu.cstad.bankingapi.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTypeInitialize {

    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    public void init() {
        if(accountTypeRepository.count() == 0){
            AccountType saving = new AccountType();

            saving.setType("SAVING");
            saving.setIsDeleted(false);

            AccountType payroll = new AccountType();

            payroll.setType("PAYROLL");
            payroll.setIsDeleted(false);

            accountTypeRepository.save(saving);
            accountTypeRepository.save(payroll);
        }

    }
}
