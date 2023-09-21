package global.citytech.cash.service.adapter.converter;

import global.citytech.cash.service.adapter.dto.CashDepositDto;
import global.citytech.cash.repository.Cash;

public class CashDepositDtoToCash {

    public  static Cash toCash(CashDepositDto cashDepositDto){
    Cash cash = new Cash();
    cash.setUsername(cashDepositDto.getUsername());
    cash.setAmount(cashDepositDto.getAmount());
    return cash;
}
}
