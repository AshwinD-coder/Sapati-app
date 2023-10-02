package global.citytech.cash.service.update;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.acceptreject.AcceptRejectRequest;
import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.accept.PaybackAcceptRequest;
import jakarta.inject.Inject;

import java.util.Optional;

public class CashRepositoryUpdateService {
    @Inject
    CashRepository cashRepository;
    @Inject
    BorrowRepository borrowRepository;

    @Inject
    PaybackRepository paybackRepository;

    public CashRepositoryUpdateService(CashRepository cashRepository, BorrowRepository borrowRepository, PaybackRepository paybackRepository) {
        this.cashRepository = cashRepository;
        this.borrowRepository = borrowRepository;
        this.paybackRepository = paybackRepository;
    }

    public void updateCashRepositoryForBorrow(AcceptRejectRequest acceptRejectRequest) {
        Optional<Cash> lenderCashAccount = this.cashRepository.findByUsername(acceptRejectRequest.getLenderUsername());
        Optional<Borrow> borrowRequest = this.borrowRepository.findByTransactionId(acceptRejectRequest.getTransactionId());
        if(borrowRequest.isEmpty()){
            throw new IllegalArgumentException("Couldn't find money request!");
        }
        Optional<Cash> borrowerCashAccount = this.cashRepository.findByUsername(borrowRequest.get().getBorrower());
        if(borrowerCashAccount.isEmpty() || lenderCashAccount.isEmpty()){
            throw new IllegalArgumentException("Couldn't find borrower or lender cash account!");
        }
        Cash lenderCash = lenderCashAccount.get();
        Cash borrowerCash = borrowerCashAccount.get();
        lenderCash.setAmount(lenderCash.getAmount() - borrowRequest.get().getAmount());
        borrowerCash.setAmount(borrowerCash.getAmount() + borrowRequest.get().getAmount());
        this.cashRepository.update(borrowerCash);
        this.cashRepository.update(lenderCash);
    }

    public void updateCashRepositoryForPayback(PaybackAcceptRequest paybackAcceptRequest,Double totalAmount) {
        Optional<Payback> payback = this.paybackRepository.findById(paybackAcceptRequest.getTransactionId());
        if(payback.isEmpty()){
            throw new IllegalArgumentException("Couldn't find money request!");
        }
        Optional<Cash> lenderCashAccount = this.cashRepository.findByUsername(payback.get().getLender());
        Optional<Cash> borrowerCashAccount = this.cashRepository.findByUsername(payback.get().getBorrower());
        if(borrowerCashAccount.isEmpty() || lenderCashAccount.isEmpty()){
            throw new IllegalArgumentException("Couldn't find borrower or lender cash account!");
        }
        Cash lenderCash = lenderCashAccount.get();
        Cash borrowerCash = borrowerCashAccount.get();
        lenderCash.setAmount(lenderCash.getAmount() + totalAmount);
        borrowerCash.setAmount(borrowerCash.getAmount() - totalAmount);
        this.cashRepository.update(borrowerCash);
        this.cashRepository.update(lenderCash);
    }
}
