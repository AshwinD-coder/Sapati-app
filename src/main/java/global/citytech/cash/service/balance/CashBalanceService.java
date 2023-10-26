package global.citytech.cash.service.balance;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.cash.service.adapter.converter.CashToCashBalanceResponse;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.security.ContextHolder;
import jakarta.inject.Inject;

import java.util.Optional;

public class CashBalanceService {
    @Inject
    private CashRepository cashRepository;

    public CashBalanceService(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }

    public CustomResponseHandler<CashBalanceResponse> getCashBalance() {
        Optional<Cash> cash = this.cashRepository.findByUsername(ContextHolder.get().getUsername());
        if (cash.isEmpty()) {
            throw new IllegalArgumentException("No cash account found!");
        }
        CashBalanceResponse cashBalanceResponse = CashToCashBalanceResponse.toCashBalanceResponse(cash.get());
        return new CustomResponseHandler<>("0", "Cash Account Balance:::", cashBalanceResponse);
    }
}
