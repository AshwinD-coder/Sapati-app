package global.citytech.cash.service.deposit;

import global.citytech.cash.service.adapter.converter.ManageCashRequestConverter;
import global.citytech.cash.service.adapter.dto.CashDto;
import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import jakarta.inject.Inject;

public class CashDepositService {

    @Inject
    private CashRepository cashRepository;


    public CashDepositService(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }
    public String depositAmount(CashDto cashDto){
        Cash cash = ManageCashRequestConverter.toCash(cashDto);
        this.cashRepository.save(cash);
        return "Deposited Amount: "+cashDto.getAmount();
    }
}
