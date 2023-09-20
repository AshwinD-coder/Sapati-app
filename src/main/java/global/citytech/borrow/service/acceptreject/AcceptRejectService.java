package global.citytech.borrow.service.acceptreject;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.platform.common.enums.RequestStatus;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Transactional(Transactional.TxType.REQUIRES_NEW)

public class AcceptRejectService {
    @Inject
    BorrowRepository borrowRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CashRepository cashRepository;

    public AcceptRejectService(BorrowRepository borrowRepository, UserRepository userRepository, CashRepository cashRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
    }

    public CustomResponseHandler<AcceptRejectResponse> acceptRequest(AcceptRejectRequest acceptRejectRequest) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Optional<Cash> lenderAccount = this.cashRepository.findByUsername(acceptRejectRequest.getLenderUsername());
        Borrow request = moneyRequest.get();
        validateRequest(acceptRejectRequest);
        checkAvailableCash(lenderAccount.get(), request);
        updateCashRepository(acceptRejectRequest);
        request.setRequestStatus(RequestStatus.ACCEPTED);
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

    public void updateCashRepository(AcceptRejectRequest acceptRejectRequest) {
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

    public CustomResponseHandler<AcceptRejectResponse> rejectRequest(AcceptRejectRequest acceptRejectRequest) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Borrow request = moneyRequest.get();
        validateRequest(acceptRejectRequest);
        request.setRequestStatus(RequestStatus.REJECTED);
        this.borrowRepository.update(request);
        AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(), request.getRequestStatus());
        return new CustomResponseHandler<>("0", "Money Request Rejected!", acceptRejectResponse);

    }


}
