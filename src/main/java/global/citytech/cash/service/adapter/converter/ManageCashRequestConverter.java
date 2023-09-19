package global.citytech.cash.service.adapter.converter;

import global.citytech.cash.service.adapter.dto.CashDto;
import global.citytech.cash.repository.Cash;

public class ManageCashRequestConverter {
public static Cash toCash(CashDto cashDto){
    Cash cash = new Cash();
    cash.setUsername(cashDto.getUsername());
    cash.setAmount(cashDto.getAmount());
    return cash;
}
}
