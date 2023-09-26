package global.citytech.borrow.service.adapter.converter;

import global.citytech.borrow.repository.Borrow;
import global.citytech.payback.repository.Payback;
import global.citytech.platform.common.enums.PaybackStatus;

import java.text.SimpleDateFormat;

public class BorrowToPayback {
    public static Payback toPayback(Borrow borrow){
        Payback payback = new Payback();
        payback.setId(borrow.getTransactionId());
        payback.setBorrower(borrow.getBorrower());
        payback.setLender(borrow.getLender());
        payback.setPaybackAmount(borrow.getAmount());
        payback.setPaybackDeadline(borrow.getReturnDate());
        payback.setPaybackStatus(PaybackStatus.UNPAID);
        payback.setPaybackCompletedOn(null);
        return payback;
    }
}
