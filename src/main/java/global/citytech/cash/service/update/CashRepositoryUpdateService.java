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
        Optional<Cash> borrowerCashAccount = this.cashRepository.findByUsername(borrowRequest.get().getBorrower());
        Cash lenderCash = lenderCashAccount.get();
        Cash borrowerCash = borrowerCashAccount.get();
        lenderCash.setAmount(lenderCash.getAmount() - borrowRequest.get().getAmount());
        borrowerCash.setAmount(borrowerCash.getAmount() + borrowRequest.get().getAmount());
        this.cashRepository.update(borrowerCash);
        this.cashRepository.update(lenderCash);
    }
    public void updateCashRepositoryForPayback(PaybackAcceptRequest paybackAcceptRequest) {
        Optional<Payback> payback = this.paybackRepository.findById(paybackAcceptRequest.getTransactionId());
        Optional<Cash> lenderCashAccount = this.cashRepository.findByUsername(payback.get().getLender());
        Optional<Cash> borrowerCashAccount = this.cashRepository.findByUsername(payback.get().getBorrower());
        Cash lenderCash = lenderCashAccount.get();
        Cash borrowerCash = borrowerCashAccount.get();
        lenderCash.setAmount(lenderCash.getAmount() + payback.get().getPaybackAmount());
        borrowerCash.setAmount(borrowerCash.getAmount() - payback.get().getPaybackAmount());
        this.cashRepository.update(borrowerCash);
        this.cashRepository.update(lenderCash);
    }
}
