package global.citytech.borrow.service.acceptreject;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.adapter.converter.BorrowToPayback;
import global.citytech.borrow.service.mail.BorrowMailService;
import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.cash.service.update.CashRepositoryUpdateService;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.platform.common.enums.RequestStatus;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.common.email.EmailConfiguration;
import global.citytech.platform.common.email.EmailSender;
import global.citytech.platform.security.ContextHolder;
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
    CashRepositoryUpdateService cashRepositoryUpdateService;
    @Inject
    BorrowMailService borrowMailService;

    public AcceptRejectService(BorrowRepository borrowRepository, UserRepository userRepository, CashRepository cashRepository, PaybackRepository paybackRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.paybackRepository = paybackRepository;
    }

    public CustomResponseHandler<AcceptRejectResponse> acceptRequest(AcceptRejectRequest acceptRejectRequest) {
        validateRequest(acceptRejectRequest);
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Optional<Cash> lenderAccount = this.cashRepository.findByUsername(ContextHolder.get().getUsername());
        if (moneyRequest.isEmpty() || lenderAccount.isEmpty()) {
            throw new IllegalArgumentException("Transaction Id Invalid!");
        }
        Borrow request = moneyRequest.get();
        checkAvailableCash(lenderAccount.get(), request);
        cashRepositoryUpdateService.updateCashRepositoryForBorrow(acceptRejectRequest);
        request.setRequestStatus(RequestStatus.ACCEPTED);
        addPayback(acceptRejectRequest.getTransactionId());
        this.borrowRepository.update(request);
        EmailConfiguration emailConfiguration = borrowMailService.setEmailConfigurationOfAcceptedBorrowMailForBorrower(request);
        EmailSender.sendMail(emailConfiguration);
        AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(),request.getInterestRate(),lenderAccount.get().getAmount()-request.getAmount(), request.getReturnDate().toString(),request.getRequestStatus());
        return new CustomResponseHandler<>("0", "Money Request Accepted!", acceptRejectResponse);
    }

    public void validateRequest(AcceptRejectRequest acceptRejectRequest) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Optional<User> user = this.userRepository.findByUsername(ContextHolder.get().getUsername());
        if (moneyRequest.isEmpty()) {
            throw new IllegalArgumentException("Invalid money request id!");
        }
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid lender!");
        }
        if (user.get().getUserType() != UserType.LENDER) {
            throw new IllegalArgumentException("Only lender can accept or reject request!");
        }
        if (user.get().getActiveStatus().equals(false)) {
            throw new IllegalArgumentException("Lender is not active!");
        }
        Optional<User> borrower = this.userRepository.findByUsername(moneyRequest.get().getBorrower());
        if (borrower.isEmpty()) {
            throw new IllegalArgumentException("Borrower not found!");
        }
        if (borrower.get().getActiveStatus().equals(false)) {
            throw new IllegalArgumentException("Borrower is not active!");
        }
        if (borrower.get().getBlacklistStatus().equals(true)) {
            throw new IllegalArgumentException("Borrower is blacklisted!");
        }
        if (moneyRequest.get().getRequestStatus() != RequestStatus.PENDING) {
            throw new IllegalArgumentException("Money Request already accepted, rejected or expired!");
        }
        if (!moneyRequest.get().getLender().equals(ContextHolder.get().getUsername())) {
            throw new IllegalArgumentException("This lender is not associated with that transaction Id");
        }
    }

    public void checkAvailableCash(Cash cash, Borrow borrow) {
        if (cash.getAmount() < borrow.getAmount()) {
            throw new IllegalArgumentException("You have insufficient funds!");
        }
    }

    private void addPayback(UUID transactionId) {
        Optional<Borrow> borrow = this.borrowRepository.findById(transactionId);
        if (borrow.isEmpty()) {
            throw new IllegalArgumentException("Money Request not found!");
        }
        Payback payback = BorrowToPayback.toPayback(borrow.get());
        this.paybackRepository.save(payback);
    }

    public CustomResponseHandler<AcceptRejectResponse> rejectRequest(AcceptRejectRequest acceptRejectRequest) {
        validateRequest(acceptRejectRequest);
        Optional<Borrow> moneyRequest = this.borrowRepository.findById(acceptRejectRequest.getTransactionId());
        Optional<Cash> lenderAccount = this.cashRepository.findByUsername(ContextHolder.get().getUsername());
        if (moneyRequest.isEmpty()) {
            throw new IllegalArgumentException("Money request not found!");
        }
        Borrow request = moneyRequest.get();
        request.setRequestStatus(RequestStatus.REJECTED);
        this.borrowRepository.update(request);
        EmailConfiguration emailConfiguration = borrowMailService.setEmailConfigurationOfRejectedBorrowMailForBorrower(request);
        EmailSender.sendMail(emailConfiguration);
        AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(),request.getInterestRate(),lenderAccount.get().getAmount(), request.getReturnDate().toString(),request.getRequestStatus());
        return new CustomResponseHandler<>("0", "Money Request Rejected!", acceptRejectResponse);
    }


}
