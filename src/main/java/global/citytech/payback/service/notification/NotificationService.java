package global.citytech.payback.service.notification;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.mail.PaybackMailService;
import global.citytech.platform.common.email.EmailConfiguration;
import global.citytech.platform.common.email.EmailSender;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class NotificationService {
    @Inject
    PaybackRepository paybackRepository;
    @Inject
    UserRepository userRepository;

    public void checkForDeadline(String username) {
        List<Payback> unpaidPaybacks = this.paybackRepository.findByBorrowerOrLenderAndPaybackStatusIn(username, username, PaybackStatus.UNPAID);
        LocalDate currentDate = LocalDate.now();
        for (Payback payback : unpaidPaybacks
        ) {
            LocalDate paybackDeadline = LocalDate.parse(payback.getPaybackDeadline().toString());
            System.out.println(paybackDeadline);
            System.out.println(currentDate);
            System.out.println(Math.abs(ChronoUnit.DAYS.between(paybackDeadline, currentDate)));
            if (Math.abs(ChronoUnit.DAYS.between(paybackDeadline, currentDate)) <= 3) {
                System.out.println(paybackDeadline);
                System.out.println(currentDate);
                System.out.println(Math.abs(ChronoUnit.DAYS.between(paybackDeadline, currentDate)));
                System.out.println("send email.");
                EmailConfiguration emailConfiguration = new PaybackMailService(paybackRepository, userRepository).setEmailConfigurationForDeadline(payback);
                EmailSender.sendMail(emailConfiguration);
            }
        }
    }
}
