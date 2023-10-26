package global.citytech.payback.service.paybackpage;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.adapter.converter.PaybackToPaybackPageResponseList;
import global.citytech.payback.service.interest.InterestService;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.security.ContextHolder;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaybackPageService {
    @Inject
    PaybackRepository paybackRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    InterestService interestService;

    public PaybackPageService(PaybackRepository paybackRepository, UserRepository userRepository) {
        this.paybackRepository = paybackRepository;
        this.userRepository = userRepository;
    }

    public void validatePaybackRequest(String username) {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
    }

    public CustomResponseHandler<List<PaybackPageResponse>> viewPaidPaybackPage() {
        validatePaybackRequest(ContextHolder.get().getUsername());
        List<Payback> paidPaybackPageList = this.paybackRepository.findByBorrowerOrLenderAndPaybackStatusIn(ContextHolder.get().getUsername(), ContextHolder.get().getUsername(), PaybackStatus.PAID);
        if (paidPaybackPageList.isEmpty()) {
            throw new IllegalArgumentException("Paid Payback list empty!");
        }
        List<PaybackPageResponse> paidPaybackPageResponseList = PaybackToPaybackPageResponseList.toPaybackPageResponseList(paidPaybackPageList);
        return new CustomResponseHandler<>("0", "Paid Payback Page Retrieved!", paidPaybackPageResponseList);
    }

    public CustomResponseHandler<List<PaybackPageResponse>> viewUnpaidPaybackPage() {
        validatePaybackRequest(ContextHolder.get().getUsername());
        List<Payback> unpaidPaybackPageList = this.paybackRepository.findByBorrowerOrLenderAndPaybackStatusIn(ContextHolder.get().getUsername(), ContextHolder.get().getUsername(), PaybackStatus.UNPAID);
        if (unpaidPaybackPageList.isEmpty()) {
            throw new IllegalArgumentException("Unpaid Payback list empty!");
        }
        List<PaybackPageResponse> paybackPageResponseList = new ArrayList<>();
        for (Payback payback:unpaidPaybackPageList
             ) {
            Double interest = interestService.calculateInterestForPaybackAccept(payback.getId());

            paybackPageResponseList.add(PaybackToPaybackPageResponseList.toPaybackPageResponseList(payback,interest));
        }

        return new CustomResponseHandler<>("0", "Unpaid Payback Page Retrieved!", paybackPageResponseList);
    }

}
