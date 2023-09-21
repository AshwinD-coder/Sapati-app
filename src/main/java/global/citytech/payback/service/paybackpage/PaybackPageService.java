package global.citytech.payback.service.paybackpage;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.adapter.converter.PaybackToPaybackPageResponseList;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.platform.common.response.CustomResponseHandler;
import jakarta.inject.Inject;

import java.util.List;

public class PaybackPageService {
    @Inject
    PaybackRepository paybackRepository;


    public PaybackPageService(PaybackRepository paybackRepository) {
        this.paybackRepository = paybackRepository;
    }
    public CustomResponseHandler<List<PaybackPageResponse>> viewPaidPaybackPage(PaybackPageRequest paybackPageRequest){
        List<Payback> paidPaybackPageList = this.paybackRepository.findByBorrowerOrLenderAndPaybackStatusIn(paybackPageRequest.getUsername(),paybackPageRequest.getUsername(), PaybackStatus.PAID);
        if(paidPaybackPageList.isEmpty()){
            throw new IllegalArgumentException("Paid Payback list empty!");
        }
        List<PaybackPageResponse> paybackPageResponseList = PaybackToPaybackPageResponseList.toPaybackPageResponseList(paidPaybackPageList);
        return new CustomResponseHandler<>("0","Payback Page Retrieved!",paybackPageResponseList);
    }

}
