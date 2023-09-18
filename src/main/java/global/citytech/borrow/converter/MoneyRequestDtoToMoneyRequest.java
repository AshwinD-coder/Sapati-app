package global.citytech.borrow.converter;

import global.citytech.borrow.dto.MoneyRequestDto;
import global.citytech.borrow.model.MoneyRequest;
import global.citytech.borrow.model.RequestStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoneyRequestDtoToMoneyRequest {

    public static MoneyRequest toMoneyRequest(MoneyRequestDto moneyRequestDto) throws ParseException {
        MoneyRequest moneyRequest = new MoneyRequest();
        moneyRequest.setRequestFrom(moneyRequestDto.getRequestFrom());
        moneyRequest.setRequestTo(moneyRequestDto.getRequestTo());
        moneyRequest.setAmount(moneyRequestDto.getAmount());
        moneyRequest.setRemarks(moneyRequestDto.getRemarks());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        moneyRequest.setReturnDate(simpleDateFormat.parse(moneyRequestDto.getReturnDate()));
        moneyRequest.setRequestStatus(RequestStatus.PENDING);
        return moneyRequest;
    }
}
