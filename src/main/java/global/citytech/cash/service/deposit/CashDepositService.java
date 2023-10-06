package global.citytech.cash.service.deposit;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.cash.service.adapter.dto.CashDepositDto;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.security.ContextHolder;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class CashDepositService {

    @Inject
    private CashRepository cashRepository;

    @Inject
    private UserRepository userRepository;

    public CashDepositService(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }

    public void validateCashDeposit(CashDepositDto cashDepositDto) {
        Optional<User> user = this.userRepository.findByUsername(ContextHolder.get().getUsername());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No user cash account to deposit!");
        }
        if (user.get().getVerifyStatus().equals(false)) {
            throw new IllegalArgumentException("User not verified!");
        }
        if (user.get().getActiveStatus().equals(false)) {
            throw new IllegalArgumentException("User is not active!");
        }
        if (user.get().getUserType().equals(UserType.ADMIN)) {
            throw new IllegalArgumentException("Admin cannot deposit money!");
        }
        if (cashDepositDto.getAmount() <= 0) {
            throw new IllegalArgumentException("Deposit at least Nrs.1");
        }

    }

    public CustomResponseHandler<CashDepositResponse> depositAmount(CashDepositDto cashDepositDto) {
        validateCashDeposit(cashDepositDto);
        Optional<User> user = this.userRepository.findByUsername(ContextHolder.get().getUsername());
        Optional<Cash> cashAccount = this.cashRepository.findByUsername(ContextHolder.get().getUsername());
        if(user.isEmpty() || cashAccount.isEmpty()){
            throw new IllegalArgumentException("No user or cash account found!");
        }
        Cash cash = cashAccount.get();
        cash.setAmount(cash.getAmount() + cashDepositDto.getAmount());
        cash.setUserType(user.get().getUserType());
        this.cashRepository.update(cash);
        CashDepositResponse cashDepositResponse = new CashDepositResponse(ContextHolder.get().getUsername(), cashDepositDto.getAmount(),ContextHolder.get().getUserType());
        return new CustomResponseHandler<>("200", "Money Deposited!!", cashDepositResponse);
    }
}
