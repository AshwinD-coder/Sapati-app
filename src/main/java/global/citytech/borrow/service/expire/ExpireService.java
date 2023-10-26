package global.citytech.borrow.service.expire;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.mail.BorrowMailService;
import global.citytech.platform.common.enums.RequestStatus;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

public class ExpireService {
    @Inject
    BorrowRepository borrowRepository;
    @Inject
    BorrowMailService borrowMailService;

    public ExpireService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public void expireMoneyRequestsAndSendMail() {
        List<Borrow> borrowList = this.borrowRepository.findByRequestStatusIn(RequestStatus.PENDING);
        for (Borrow borrow : borrowList) {
            int daysCrossed = getDaysCrossed(borrow);
            if (daysCrossed == 2 && borrow.getRequestStatus().equals(RequestStatus.PENDING)) {
                borrow.setRequestStatus(RequestStatus.EXPIRED);
                this.borrowRepository.update(borrow);
//                sendExpiredMail(borrow);
            }
        }
    }

//    private void sendExpiredMail(Borrow borrow) {
//        EmailConfiguration emailConfiguration = borrowMailService.setEmailConfigurationForExpire(borrow);
//        EmailService.sendMail(emailConfiguration);
//
//    }

    private int getDaysCrossed(Borrow borrow) {
        Date requestDate = borrow.getRequestedAt();
        Date currentDate = new Date();
        long timeInMilliseconds = currentDate.getTime() - requestDate.getTime(); // 1 Day = 86400000 milliseconds
        if (currentDate.after(requestDate) && timeInMilliseconds >= 86400000 * 2) {
            return 2;
        } else {
            return 0;
        }
    }
}
