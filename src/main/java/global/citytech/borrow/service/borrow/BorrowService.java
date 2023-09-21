package global.citytech.borrow.service.borrow;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.adapter.converter.BorrowDtoToBorrow;
import global.citytech.borrow.service.adapter.converter.BorrowToPayback;
import global.citytech.borrow.service.adapter.dto.BorrowDto;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.platform.common.enums.RequestStatus;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class BorrowService {
    @Inject
    private BorrowRepository borrowRepository;
    @Inject
    private UserRepository userRepository;

    public BorrowService(BorrowRepository borrowRepository, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }

    public CustomResponseHandler<String> requestMoney(BorrowDto borrowDto) throws ParseException {
        Borrow borrow = BorrowDtoToBorrow.toBorrow(borrowDto);
        validateRequest(borrowDto);
        this.borrowRepository.save(borrow);

        return new CustomResponseHandler<>("0", "Money Request Complete!", null);
    }




    public void validateReturnDate(BorrowDto borrowDto) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date returnDate = simpleDateFormat.parse(borrowDto.getReturnDate());
        if (currentDate.after(returnDate)) {
            throw new IllegalArgumentException("Return date must be at least a day after current date i.e " + currentDate);
        }
    }

    public void validateAmount(BorrowDto borrowDto) {
        if(borrowDto.getAmount()<0){
            throw new IllegalArgumentException("Amount cannot be negative!");
        } if(borrowDto.getAmount()==0){
            throw new IllegalArgumentException("Amount cannot be zero!");
        }
        if (borrowDto.getAmount() > 50000 ) {
            throw new IllegalArgumentException("Request Amount Limit Exceeded! Max Limit Nrs.50000");
        }
        if (borrowDto.getAmount().toString().isBlank() || borrowDto.getAmount().toString().isEmpty()) {
            throw new IllegalArgumentException("Request Amount cannot be empty or blank!");
        }
    }

    private void validatePreviousRequest(BorrowDto borrowDto) {
        Optional<Borrow> moneyRequest = this.borrowRepository.findByBorrowerAndLenderAndRequestStatus(borrowDto.getBorrower(), borrowDto.getLender(), RequestStatus.PENDING);
        if (moneyRequest.isPresent()) {
            throw new IllegalArgumentException("You have previous request pending!");
        }
    }

    public void validateUsers(BorrowDto borrowDto) {
        Optional<User> userRequestTo = this.userRepository.findByUsername(borrowDto.getLender());
        Optional<User> userRequestFrom = this.userRepository.findByUsername(borrowDto.getBorrower());
        if (userRequestTo.isEmpty() || userRequestFrom.isEmpty()) {
            throw new IllegalArgumentException("No such user to request!");
        }
        if (!userRequestFrom.get().getVerifyStatus()) {
            throw new IllegalArgumentException("Borrower not verified!");
        }
        if (!userRequestTo.get().getVerifyStatus()) {
            throw new IllegalArgumentException("Lender not verified!");
        }
        if (userRequestFrom.get().getUserType().compareTo(UserType.BORROWER) != 0) {
            throw new IllegalArgumentException("Only Borrower can request!");
        }
        if (userRequestTo.get().getUserType().compareTo(UserType.LENDER) != 0) {
            throw new IllegalArgumentException("Only Lender can be requested for money!");
        }
    }

    public void validateRequest(BorrowDto borrowDto) throws ParseException {
        validateUsers(borrowDto);
        validatePreviousRequest(borrowDto);
        validateAmount(borrowDto);
        validateReturnDate(borrowDto);
    }


}
