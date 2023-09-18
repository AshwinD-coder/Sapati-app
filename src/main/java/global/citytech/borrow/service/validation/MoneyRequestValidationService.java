package global.citytech.borrow.service.validation;

import global.citytech.borrow.dto.MoneyRequestDto;
import global.citytech.borrow.repository.MoneyRequestRepository;
import global.citytech.user.model.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class MoneyRequestValidationService {
    @Inject
    private MoneyRequestRepository moneyRequestRepository;

    @Inject
    private UserRepository userRepository;

    public MoneyRequestValidationService(MoneyRequestRepository moneyRequestRepository) {
        this.moneyRequestRepository = moneyRequestRepository;
    }

    public void validateUsers(MoneyRequestDto moneyRequestDto){
        Optional<User> userRequestTo = this.userRepository.findByUsername(moneyRequestDto.getRequestTo());
        Optional<User> userRequestFrom = this.userRepository.findByUsername(moneyRequestDto.getRequestTo());
        if(userRequestTo.isEmpty()){
            throw new IllegalArgumentException("No such user to request!");
        }
        if(!userRequestTo.get().getVerifyStatus()){
            throw  new IllegalArgumentException("You cannot request an unverified user!");
        }
    }

    public void validateAmount(MoneyRequestDto moneyRequestDto){
        if(moneyRequestDto.getAmount()>50000 && moneyRequestDto.getAmount()<0){
            throw  new IllegalArgumentException("Limit Exceeded! Max Limit(0-50000)");
        }
    }
    public void validate(MoneyRequestDto moneyRequestDto){
        validateUsers(moneyRequestDto);
        validateAmount(moneyRequestDto);
    }
}
