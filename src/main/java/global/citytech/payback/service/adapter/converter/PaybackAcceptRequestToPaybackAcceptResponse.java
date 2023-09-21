package global.citytech.payback.service.adapter.converter;

import global.citytech.payback.service.accept.PaybackAcceptRequest;
import global.citytech.payback.service.accept.PaybackAcceptResponse;
import global.citytech.platform.common.enums.PaybackStatus;

public class PaybackAcceptRequestToPaybackAcceptResponse {
    public static PaybackAcceptResponse toPaybackAcceptResponse(PaybackAcceptRequest paybackAcceptRequest){
        PaybackAcceptResponse paybackAcceptResponse = new PaybackAcceptResponse();
        paybackAcceptResponse.setTransactionId(paybackAcceptRequest.getTransactionId());
        paybackAcceptResponse.setPaybackStatus(PaybackStatus.PAID);
        return paybackAcceptResponse;
    }
}
