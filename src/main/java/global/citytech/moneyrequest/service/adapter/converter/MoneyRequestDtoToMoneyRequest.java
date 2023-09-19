package global.citytech.moneyrequest.service.adapter.converter;

import global.citytech.moneyrequest.service.adapter.dto.MoneyRequestDto;
import global.citytech.moneyrequest.repository.MoneyRequest;
import global.citytech.moneyrequest.repository.RequestStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MoneyRequestDtoToMoneyRequest {

    public static MoneyRequest toMoneyRequest(MoneyRequestDto moneyRequestDto) throws ParseException {
        MoneyRequest moneyRequest = new MoneyRequest();
        moneyRequest.setBorrower(moneyRequestDto.getBorrower());
        moneyRequest.setLender(moneyRequestDto.getLender());
        moneyRequest.setAmount(moneyRequestDto.getAmount());
        moneyRequest.setRemarks(moneyRequestDto.getRemarks());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        moneyRequest.setReturnDate(simpleDateFormat.parse(moneyRequestDto.getReturnDate()));
        moneyRequest.setRequestStatus(RequestStatus.PENDING);
        return moneyRequest;
    }
}
