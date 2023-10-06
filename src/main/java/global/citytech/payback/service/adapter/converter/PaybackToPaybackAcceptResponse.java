package global.citytech.payback.service.adapter.converter;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.service.accept.PaybackAcceptRequest;
import global.citytech.payback.service.accept.PaybackAcceptResponse;
import global.citytech.platform.common.enums.PaybackStatus;
import jakarta.inject.Inject;

import java.util.Optional;

public class PaybackToPaybackAcceptResponse {

    private PaybackToPaybackAcceptResponse(){}
    public static PaybackAcceptResponse toPaybackAcceptResponse(Payback payback,Double borrowerAmount){
        PaybackAcceptResponse paybackAcceptResponse = new PaybackAcceptResponse();
        paybackAcceptResponse.setTransactionId(payback.getId());
        paybackAcceptResponse.setPaybackStatus(payback.getPaybackStatus());
        paybackAcceptResponse.setPaybackCompletedOn(payback.getPaybackCompletedOn().toString());
        paybackAcceptResponse.setTotalPaybackWithInterest(payback.getTotalPaybackAmountWithInterest());
        paybackAcceptResponse.setRemainingAmount(borrowerAmount - payback.getTotalPaybackAmountWithInterest());
        return paybackAcceptResponse;
    }
}
