package global.citytech.payback.service.accept;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.cash.service.update.CashRepositoryUpdateService;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.adapter.converter.PaybackAcceptRequestToPaybackAcceptResponse;
import global.citytech.payback.service.interest.InterestService;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class PaybackAcceptService {
    @Inject
    PaybackRepository paybackRepository;

    @Inject
    UserRepository userRepository;
    @Inject
    CashRepository cashRepository;

    @Inject
    CashRepositoryUpdateService cashRepositoryUpdateService;

    @Inject
    private InterestService interestService;

    public PaybackAcceptService(PaybackRepository paybackRepository, UserRepository userRepository, CashRepository cashRepository, CashRepositoryUpdateService cashRepositoryUpdateService) {
        this.paybackRepository = paybackRepository;
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.cashRepositoryUpdateService = cashRepositoryUpdateService;
    }

    public CustomResponseHandler<PaybackAcceptResponse> acceptPayback(PaybackAcceptRequest paybackAcceptRequest){
        UUID transactionId = paybackAcceptRequest.getTransactionId();
        String borrower = paybackAcceptRequest.getBorrower();
        validateAcceptPayback(transactionId,borrower);
        Optional<Payback> payback = this.paybackRepository.findById(transactionId);
        if(payback.isEmpty()){
            throw new IllegalArgumentException("Payback not found!");
        }
        Double totalAmount = payback.get().getPaybackAmount() + interestService.calculateInterestForPaybackAccept(payback.get().getId());
        checkBalance(borrower,totalAmount);
        cashRepositoryUpdateService.updateCashRepositoryForPayback(paybackAcceptRequest,totalAmount);
        payback.get().setPaybackStatus(PaybackStatus.PAID);
        payback.get().setPaybackCompletedOn(new Timestamp(new Date().getTime()));
        payback.get().setTotalPaybackAmountWithInterest(totalAmount);
        this.paybackRepository.update(payback.get());
        PaybackAcceptResponse paybackAcceptResponse = PaybackAcceptRequestToPaybackAcceptResponse.toPaybackAcceptResponse(paybackAcceptRequest);
        return new CustomResponseHandler<>("0","Payback Accepted!",paybackAcceptResponse);
    }

    private void validateAcceptPayback(UUID transactionId, String borrower) {
        Optional<Payback> payback = this.paybackRepository.findById(transactionId);
        Optional<Cash> borrowerCash = this.cashRepository.findByUsername(borrower);
        if(payback.isEmpty()){
            throw  new IllegalArgumentException("Payback transaction id not found!");
        }
        if(!payback.get().getBorrower().equals(borrower)){
            throw new IllegalArgumentException("Invalid borrower!");
        }
        if(payback.get().getPaybackStatus().equals(PaybackStatus.PAID)) {
            throw new IllegalArgumentException("Payback already paid!");
        }
        if(borrowerCash.isEmpty()){
            throw new IllegalArgumentException("Borrower Cash Account not found!");
        }

    }
    public void checkBalance(String borrower, Double totalAmount){
        Optional<Cash> borrowerCash = this.cashRepository.findByUsername(borrower);
        if(borrowerCash.isEmpty()){
            throw new IllegalArgumentException("Borrower Cash Account Not Found!");
        }
        if(borrowerCash.get().getAmount() < totalAmount){
            throw new IllegalArgumentException("You have insufficient funds for payback!");
        }
    }
}
