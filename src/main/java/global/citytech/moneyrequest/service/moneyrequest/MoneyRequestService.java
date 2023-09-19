package global.citytech.moneyrequest.service.moneyrequest;

import global.citytech.moneyrequest.repository.RequestStatus;
import global.citytech.moneyrequest.service.adapter.converter.MoneyRequestDtoToMoneyRequest;
import global.citytech.moneyrequest.service.adapter.dto.MoneyRequestDto;
import global.citytech.moneyrequest.repository.MoneyRequest;
import global.citytech.moneyrequest.repository.MoneyRequestRepository;
import global.citytech.platform.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.repository.UserType;
import jakarta.inject.Inject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class MoneyRequestService {
    @Inject
    private MoneyRequestRepository moneyRequestRepository;

    @Inject
    private UserRepository userRepository;

    public MoneyRequestService(MoneyRequestRepository moneyRequestRepository,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.moneyRequestRepository = moneyRequestRepository;
    }

    public CustomResponseHandler<?> requestMoney(MoneyRequestDto moneyRequestDto) throws ParseException {
        MoneyRequest moneyRequest = MoneyRequestDtoToMoneyRequest.toMoneyRequest(moneyRequestDto);
        validateRequest(moneyRequestDto);
        this.moneyRequestRepository.save(moneyRequest);
        return new CustomResponseHandler<>("200","Money Request Complete!",null);
    }

    public void validateUsers(MoneyRequestDto moneyRequestDto){
        Optional<User> userRequestTo = this.userRepository.findByUsername(moneyRequestDto.getLender());
        Optional<User> userRequestFrom = this.userRepository.findByUsername(moneyRequestDto.getBorrower());
        if(userRequestTo.isEmpty() || userRequestFrom.isEmpty()){
            throw new IllegalArgumentException("No such user to request!");
        }
        if(!userRequestFrom.get().getVerifyStatus()){
            throw  new IllegalArgumentException("Borrower not verified!");
        }
        if(!userRequestTo.get().getVerifyStatus()){
            throw  new IllegalArgumentException("Lender not verified!");
        }
        if(userRequestFrom.get().getUserType().compareTo(UserType.BORROWER)!=0) {
            throw new IllegalArgumentException("Only Borrower can request!");
        }
        if(userRequestTo.get().getUserType().compareTo(UserType.LENDER)!=0) {
            throw new IllegalArgumentException("Only Lender can be requested for money!");
        }
    }

    public void validateAmount(MoneyRequestDto moneyRequestDto){
        if(moneyRequestDto.getAmount()>50000 || moneyRequestDto.getAmount()<=0){
            throw  new IllegalArgumentException("Request Amount Limit Exceeded! Limit(Nrs.1- Nrs.50000)");
        }
        if(moneyRequestDto.getAmount().toString().isBlank() || moneyRequestDto.getAmount().toString().isEmpty()){
            throw new IllegalArgumentException("Request Amount cannot be empty or blank!");
        }
    }

    public void validateReturnDate(MoneyRequestDto moneyRequestDto) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date returnDate = simpleDateFormat.parse(moneyRequestDto.getReturnDate());
        if(currentDate.after(returnDate)){
            throw new IllegalArgumentException("Return date must be at least a day after current date i.e "+currentDate);
        }
    }
    public void validateRequest(MoneyRequestDto moneyRequestDto) throws ParseException {
        validateUsers(moneyRequestDto);
        validatePreviousRequest(moneyRequestDto);
        validateAmount(moneyRequestDto);
        validateReturnDate(moneyRequestDto);
    }

    private void validatePreviousRequest(MoneyRequestDto moneyRequestDto) {
        Optional<MoneyRequest> moneyRequest = this.moneyRequestRepository.findByBorrowerAndLenderAndRequestStatus(moneyRequestDto.getBorrower(), moneyRequestDto.getLender(), RequestStatus.PENDING);
        if(moneyRequest.isPresent()){
            throw new IllegalArgumentException("You have previous request pending!");
        }
    }

}
