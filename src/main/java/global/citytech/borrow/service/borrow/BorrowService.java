package global.citytech.borrow.service.borrow;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.adapter.converter.BorrowDtoToBorrow;
import global.citytech.borrow.service.adapter.dto.BorrowDto;
import global.citytech.borrow.service.mail.BorrowMailService;
import global.citytech.platform.common.enums.RequestStatus;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.common.email.EmailConfiguration;
import global.citytech.platform.common.email.EmailSender;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

public class BorrowService {
    @Inject
    private BorrowRepository borrowRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    BorrowMailService borrowMailService;

    public BorrowService(BorrowRepository borrowRepository, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }
    public void borrowMail(Borrow borrow){
        EmailConfiguration emailConfigurationForLender = borrowMailService.setEmailConfigurationOfBorrowMailForLender(borrow);
        EmailSender.sendMail(emailConfigurationForLender);
        EmailConfiguration emailConfigurationForBorrower = borrowMailService.setEmailConfigurationOfBorrowMailForBorrower(borrow);
        EmailSender.sendMail(emailConfigurationForBorrower);
    }

    public CustomResponseHandler<String> borrowMoney(BorrowDto borrowDto) throws ParseException {
        Borrow borrow = BorrowDtoToBorrow.toBorrow(borrowDto);
        validateRequest(borrow);
        this.borrowRepository.save(borrow);
        borrowMail(borrow);
        return new CustomResponseHandler<>("0", "Money Request Complete!", null);
    }


    public void validateReturnDate(Borrow borrow) throws ParseException {
        Date currentDate = new Date();
        if (currentDate.after(borrow.getReturnDate())) {
            throw new IllegalArgumentException("Return date must be at least a day after current date i.e " + currentDate);
        }
    }

    public void validateAmount(Borrow borrow) {
        if (borrow.getAmount() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        if (borrow.getAmount() == 0) {
            throw new IllegalArgumentException("Amount cannot be zero!");
        }
        if (borrow.getAmount() > 50000) {
            throw new IllegalArgumentException("Request Amount Limit Exceeded! Max Limit Nrs.50000");
        }
        if (borrow.getAmount().toString().isBlank() || borrow.getAmount().toString().isEmpty()) {
            throw new IllegalArgumentException("Request Amount cannot be empty or blank!");
        }
    }

    private void validatePreviousRequest(Borrow borrow) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findByBorrowerAndLenderAndRequestStatus(borrow.getBorrower(), borrow.getLender(), RequestStatus.PENDING);
        if (moneyRequest.isPresent()) {
            throw new IllegalArgumentException("You have previous request pending!");
        }
    }

    public void validateUsers(Borrow borrow) {
        Optional<User> userRequestTo = this.userRepository.findByUsername(borrow.getLender());
        Optional<User> userRequestFrom = this.userRepository.findByUsername(borrow.getBorrower());
        if (userRequestTo.isEmpty() || userRequestFrom.isEmpty()) {
            throw new IllegalArgumentException("No such user to request!");
        }
        if (Boolean.FALSE.equals(userRequestFrom.get().getVerifyStatus())) {
            throw new IllegalArgumentException("Borrower not verified!");
        }
        if (Boolean.FALSE.equals(userRequestTo.get().getVerifyStatus())) {
            throw new IllegalArgumentException("Lender not verified!");
        }
        if (Boolean.FALSE.equals(userRequestFrom.get().getActiveStatus())) {
            throw new IllegalArgumentException("Borrower is not active!");
        }
        if (Boolean.FALSE.equals(userRequestTo.get().getActiveStatus())) {
            throw new IllegalArgumentException("Lender is not active!");
        }
        if (userRequestFrom.get().getUserType().compareTo(UserType.BORROWER) != 0) {
            throw new IllegalArgumentException("Only Borrower can request!");
        }
        if (userRequestTo.get().getUserType().compareTo(UserType.LENDER) != 0) {
            throw new IllegalArgumentException("Only Lender can be requested for money!");
        }
        if(userRequestFrom.get().getBlacklistStatus().equals(true)){
            throw new IllegalArgumentException("Borrower is blacklisted!");
        }
        if(userRequestTo.get().getBlacklistStatus().equals(true)){
            throw new IllegalArgumentException("Lender is blacklisted!");
        }

    }

    public void validateRequest(Borrow borrow) throws ParseException {
        validateUsers(borrow);
        validatePreviousRequest(borrow);
        validateAmount(borrow);
        validateReturnDate(borrow);
        validateInterestRate(borrow);
    }

    private void validateInterestRate(Borrow borrow) {
        if(borrow.getInterestRate()<0.0){
            throw new IllegalArgumentException("Interest Rate cannot be less than 0.0");
        }
    }


}
