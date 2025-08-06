package kh.edu.cstad.bankingapi.mapper;

import kh.edu.cstad.bankingapi.domain.Account;
import kh.edu.cstad.bankingapi.dto.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse fromAccountToAccountResponseDto(Account accountResponse);
}
