package global.citytech.borrow.service.acceptreject;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.adapter.converter.BorrowToPayback;
import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.cash.service.update.CashRepositoryUpdateService;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.platform.common.enums.RequestStatus;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional(Transactional.TxType.REQUIRES_NEW)

public class AcceptRejectService {
    @Inject
    BorrowRepository borrowRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CashRepository cashRepository;

    @Inject
    PaybackRepository paybackRepository;

    @Inject
    CashRepositoryUpdateService updateCashRepository;
    public AcceptRejectService(BorrowRepository borrowRepository, UserRepository userRepository, CashRepository cashRepository, PaybackRepository paybackRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.paybackRepository = paybackRepository;
    }

    public CustomResponseHandler<AcceptRejectResponse> acceptRequest(AcceptRejectRequest acceptRejectRequest) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Optional<Cash> lenderAccount = this.cashRepository.findByUsername(acceptRejectRequest.getLenderUsername());
        Borrow request = moneyRequest.get();
        validateRequest(acceptRejectRequest);
        checkAvailableCash(lenderAccount.get(), request);
        updateCashRepository.updateCashRepositoryForBorrow(acceptRejectRequest);
        request.setRequestStatus(RequestStatus.ACCEPTED);
        addPayback(acceptRejectRequest.getTransactionId());
        this.borrowRepository.update(request);
        AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(), request.getRequestStatus());
        return new CustomResponseHandler<>("0", "Money Request Accepted!", acceptRejectResponse);

    }

    public void validateRequest(AcceptRejectRequest acceptRejectRequest) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Optional<User> user = this.userRepository.findByUsername(acceptRejectRequest.getLenderUsername());

        if (moneyRequest.isEmpty()) {
            throw new IllegalArgumentException("Invalid money request id!");
        }
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid lender!");
        }
        if (user.get().getUserType() != UserType.LENDER) {
            throw new IllegalArgumentException("Only lender can accept or reject request!");
        }
        if (moneyRequest.get().getRequestStatus() != RequestStatus.PENDING) {
            throw new IllegalArgumentException("Money Request already accepted, rejected or expired!");
        }

    }

    public void checkAvailableCash(Cash cash, Borrow borrow) {
        if (cash.getAmount() < borrow.getAmount()) {
            throw new IllegalArgumentException("You have insufficient funds!");
        }
    }


    private void addPayback(UUID transactionId) {
        Optional<Borrow> borrow = this.borrowRepository.findById(transactionId);
        Payback payback = BorrowToPayback.toPayback(borrow.get());
        this.paybackRepository.save(payback);
    }

    public CustomResponseHandler<AcceptRejectResponse> rejectRequest(AcceptRejectRequest acceptRejectRequest) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Borrow request = moneyRequest.get();
        validateRequest(acceptRejectRequest);
        removePayback(acceptRejectRequest.getTransactionId());
        request.setRequestStatus(RequestStatus.REJECTED);
        this.borrowRepository.update(request);
        AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(), request.getRequestStatus());
        return new CustomResponseHandler<>("0", "Money Request Rejected!", acceptRejectResponse);

    }

    private void removePayback(UUID transactionId) {
        Optional<Payback> payback = this.paybackRepository.findById(transactionId);
        this.paybackRepository.delete(payback.get());
    }


}
