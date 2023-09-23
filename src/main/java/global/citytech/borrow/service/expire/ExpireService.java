package global.citytech.borrow.service.expire;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.platform.common.enums.RequestStatus;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

public class ExpireService {
    @Inject
    BorrowRepository borrowRepository;

    public ExpireService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public void expireMoneyRequests() {
        List<Borrow> borrowList = this.borrowRepository.findByRequestStatusIn(RequestStatus.PENDING);
        for (Borrow borrow :
                borrowList) {
            int daysCrossed = getDaysCrossed(borrow);

            if (daysCrossed >= 3) {
                borrow.setRequestStatus(RequestStatus.EXPIRED);
                this.borrowRepository.update(borrow);
            }
        }
    }

    private int getDaysCrossed(Borrow borrow) {
        Date requestDate = borrow.getRequestedAt();
        Date currentDate = new Date();
        System.out.println("requested at: " + requestDate);
        System.out.println("current at: " + currentDate);
        System.out.println(currentDate.compareTo(requestDate));
        return currentDate.compareTo(requestDate);
    }
}
