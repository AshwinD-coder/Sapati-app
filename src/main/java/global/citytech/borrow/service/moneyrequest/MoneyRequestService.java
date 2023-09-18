package global.citytech.borrow.service.moneyrequest;

import global.citytech.borrow.converter.MoneyRequestDtoToMoneyRequest;
import global.citytech.borrow.dto.MoneyRequestDto;
import global.citytech.borrow.model.MoneyRequest;
import global.citytech.borrow.repository.MoneyRequestRepository;
import global.citytech.borrow.service.validation.MoneyRequestValidationService;
import jakarta.inject.Inject;

import java.text.ParseException;

public class MoneyRequestService {
    @Inject
    private MoneyRequestRepository moneyRequestRepository;

    @Inject
    private MoneyRequestValidationService moneyRequestValidationService;

    public MoneyRequestService(MoneyRequestRepository moneyRequestRepository) {
        this.moneyRequestRepository = moneyRequestRepository;
    }

    public String requestMoney(MoneyRequestDto moneyRequestDto) throws ParseException {
        MoneyRequest moneyRequest = MoneyRequestDtoToMoneyRequest.toMoneyRequest(moneyRequestDto);
        moneyRequestValidationService.validate(moneyRequestDto);
        this.moneyRequestRepository.save(moneyRequest);
        return "Requested money successfully!!";
    }
}
