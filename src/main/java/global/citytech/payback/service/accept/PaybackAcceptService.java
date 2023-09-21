package global.citytech.payback.service.accept;

import global.citytech.cash.service.update.CashRepositoryUpdateService;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.adapter.converter.PaybackAcceptRequestToPaybackAcceptResponse;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.platform.common.response.CustomResponseHandler;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

public class PaybackAcceptService {
    @Inject
    PaybackRepository paybackRepository;

    @Inject
    CashRepositoryUpdateService cashRepositoryUpdateService;

    public PaybackAcceptService(PaybackRepository paybackRepository) {
        this.paybackRepository = paybackRepository;
    }

    public CustomResponseHandler<PaybackAcceptResponse> acceptPayback(PaybackAcceptRequest paybackAcceptRequest){
        UUID transactionId = paybackAcceptRequest.getTransactionId();
        String borrower = paybackAcceptRequest.getBorrower();
        validateAcceptPayback(transactionId,borrower);
        Optional<Payback> payback = this.paybackRepository.findById(transactionId);
        cashRepositoryUpdateService.updateCashRepositoryForPayback(paybackAcceptRequest);
        payback.get().setPaybackStatus(PaybackStatus.PAID);
        this.paybackRepository.update(payback.get());
        PaybackAcceptResponse paybackAcceptResponse = PaybackAcceptRequestToPaybackAcceptResponse.toPaybackAcceptResponse(paybackAcceptRequest);
        return new CustomResponseHandler<>("0","Payback Accepted!",paybackAcceptResponse);
    }

    private void validateAcceptPayback(UUID transactionId, String borrower) {
        Optional<Payback> payback = this.paybackRepository.findById(transactionId);
        if(payback.isEmpty()){
            throw  new IllegalArgumentException("Payback transaction id not found!");
        }
        if(!payback.get().getBorrower().equals(borrower)){
            throw new IllegalArgumentException("Invalid borrower!");
        }
        if(payback.get().getPaybackStatus().equals(PaybackStatus.PAID)){
            throw new IllegalArgumentException("Payback already paid!");
        }
    }
}
