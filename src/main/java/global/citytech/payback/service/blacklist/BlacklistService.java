package global.citytech.payback.service.blacklist;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BlacklistService {
    @Inject
    private PaybackRepository paybackRepository;

    @Inject
    private UserRepository userRepository;

    public BlacklistService(PaybackRepository paybackRepository, UserRepository userRepository) {
        this.paybackRepository = paybackRepository;
        this.userRepository = userRepository;
    }

    public void blacklistBorrowerIfDeadlineCrossed() {
        List<Payback> paybackList = this.paybackRepository.findByPaybackStatus(PaybackStatus.UNPAID);
        Date currentDate = new Date();
        for (Payback payback : paybackList
        ) {
            if (currentDate.after(payback.getPaybackDeadline())) {
                Optional<User> borrower = this.userRepository.findByUsername(payback.getBorrower());
                if (borrower.isPresent()) {
                    borrower.get().setBlacklistStatus(true);
                    this.userRepository.update(borrower.get());
                }

            }

        }
    }

}
