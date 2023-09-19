package global.citytech.moneyrequest.service.moneyrequest;

import global.citytech.moneyrequest.service.adapter.converter.MoneyRequestDtoToMoneyRequest;
import global.citytech.moneyrequest.service.adapter.dto.MoneyRequestDto;
import global.citytech.moneyrequest.repository.MoneyRequest;
import global.citytech.moneyrequest.repository.MoneyRequestRepository;
import global.citytech.moneyrequest.service.validation.MoneyRequestValidationService;
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
        moneyRequestValidationService.validateRequest(moneyRequestDto);
        this.moneyRequestRepository.save(moneyRequest);
        return "Requested money successfully!!";
    }
}
