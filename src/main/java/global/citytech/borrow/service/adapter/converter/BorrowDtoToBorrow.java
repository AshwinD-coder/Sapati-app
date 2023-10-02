package global.citytech.borrow.service.adapter.converter;

import global.citytech.borrow.service.adapter.dto.BorrowDto;
import global.citytech.borrow.repository.Borrow;
import global.citytech.platform.common.enums.RequestStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BorrowDtoToBorrow {
    private BorrowDtoToBorrow(){}

    public static Borrow toBorrow(BorrowDto borrowDto) throws ParseException {
        Borrow borrow = new Borrow();
        borrow.setBorrower(borrowDto.getBorrower());
        borrow.setLender(borrowDto.getLender());
        borrow.setAmount(borrowDto.getAmount());
        borrow.setInterestRate(borrowDto.getInterestRate());
        borrow.setRemarks(borrowDto.getRemarks());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        borrow.setReturnDate(simpleDateFormat.parse(borrowDto.getReturnDate()));
        borrow.setRequestStatus(RequestStatus.PENDING);
        return borrow;
    }
}
