package global.citytech.cash.service.adapter.converter;

import global.citytech.cash.service.adapter.dto.CashDepositDto;
import global.citytech.cash.repository.Cash;
import global.citytech.platform.security.ContextHolder;

public class CashDepositDtoToCash {
    private CashDepositDtoToCash(){}

    public  static Cash toCash(CashDepositDto cashDepositDto){
    Cash cash = new Cash();
    cash.setUsername(ContextHolder.get().getUsername());
    cash.setAmount(cashDepositDto.getAmount());
    return cash;
}
}
