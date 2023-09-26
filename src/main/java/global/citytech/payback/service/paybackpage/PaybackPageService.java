package global.citytech.payback.service.paybackpage;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.adapter.converter.PaybackToPaybackPageResponseList;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class PaybackPageService {
    @Inject
    PaybackRepository paybackRepository;

    @Inject
    UserRepository userRepository;

    public PaybackPageService(PaybackRepository paybackRepository, UserRepository userRepository) {
        this.paybackRepository = paybackRepository;
        this.userRepository = userRepository;
    }
    public void validatePaybackRequest(PaybackPageRequest paybackPageRequest){
        Optional<User> user = this.userRepository.findByUsername(paybackPageRequest.getUsername());
        if(user.isEmpty()){
            throw new IllegalArgumentException("User not found!");
        }
    }

    public CustomResponseHandler<List<PaybackPageResponse>> viewPaidPaybackPage(PaybackPageRequest paybackPageRequest){
        validatePaybackRequest(paybackPageRequest);
        List<Payback> paidPaybackPageList = this.paybackRepository.findByBorrowerOrLenderAndPaybackStatusIn(paybackPageRequest.getUsername(),paybackPageRequest.getUsername(), PaybackStatus.PAID);
        if(paidPaybackPageList.isEmpty()){
            throw new IllegalArgumentException("Paid Payback list empty!");
        }
        List<PaybackPageResponse> paidPaybackPageResponseList = PaybackToPaybackPageResponseList.toPaybackPageResponseList(paidPaybackPageList);
        return new CustomResponseHandler<>("0","Paid Payback Page Retrieved!",paidPaybackPageResponseList);
    }
    public CustomResponseHandler<List<PaybackPageResponse>> viewUnpaidPaybackPage(PaybackPageRequest paybackPageRequest){
        validatePaybackRequest(paybackPageRequest);
        List<Payback> unpaidPaybackPageList = this.paybackRepository.findByBorrowerOrLenderAndPaybackStatusIn(paybackPageRequest.getUsername(),paybackPageRequest.getUsername(), PaybackStatus.UNPAID);
        if(unpaidPaybackPageList.isEmpty()){
            throw new IllegalArgumentException("Unpaid Payback list empty!");
        }
        List<PaybackPageResponse> unpaidPaybackPageResponseList = PaybackToPaybackPageResponseList.toPaybackPageResponseList(unpaidPaybackPageList);
        return new CustomResponseHandler<>("0","Unpaid Payback Page Retrieved!",unpaidPaybackPageResponseList);
    }

}
