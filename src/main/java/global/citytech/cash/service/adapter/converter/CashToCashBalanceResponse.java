package global.citytech.cash.service.adapter.converter;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.service.balance.CashBalanceRequest;
import global.citytech.cash.service.balance.CashBalanceResponse;

public class CashToCashBalanceResponse {
    public static CashBalanceResponse toCashBalanceResponse(Cash cash){
        CashBalanceResponse cashBalanceResponse = new CashBalanceResponse();
        cashBalanceResponse.setUsername(cash.getUsername());
        cashBalanceResponse.setAmount(cash.getAmount());
        return cashBalanceResponse;
    }
}
