package global.citytech.payback.service.adapter.converter;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.interest.InterestService;
import global.citytech.payback.service.paybackpage.PaybackPageResponse;
import global.citytech.platform.common.enums.PaybackStatus;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaybackToPaybackPageResponseList {
    private PaybackToPaybackPageResponseList(){}

    public  static List<PaybackPageResponse> toPaybackPageResponseList(List<Payback> paybackList){
        List<PaybackPageResponse> paybackPageResponseList = new ArrayList<>();
        for (Payback payback:
                paybackList
             ) {
            PaybackPageResponse paybackPageResponse = new PaybackPageResponse();
            paybackPageResponse.setPaybackId(payback.getId());
            paybackPageResponse.setBorrower(payback.getBorrower());
            paybackPageResponse.setLender(payback.getLender());
            paybackPageResponse.setPaybackAmount(payback.getPaybackAmount());
            paybackPageResponse.setInterestRate(payback.getInterestRate());
            paybackPageResponse.setTotalPaybackWithInterest(payback.getTotalPaybackAmountWithInterest());
            paybackPageResponse.setPaybackStatus(payback.getPaybackStatus());
            paybackPageResponse.setPaybackDeadline(payback.getPaybackDeadline().toString());
            if(payback.getPaybackCompletedOn()!=null) {
                paybackPageResponse.setPaybackCompletedOn(payback.getPaybackCompletedOn().toString());
            }
            paybackPageResponseList.add(paybackPageResponse);
        }
        return paybackPageResponseList;
    } public  static List<PaybackPageResponse> toPaybackPageResponseList(List<Payback> paybackList,Double interest){
        List<PaybackPageResponse> paybackPageResponseList = new ArrayList<>();
        for (Payback payback:
                paybackList
             ) {
            PaybackPageResponse paybackPageResponse = new PaybackPageResponse();
            paybackPageResponse.setPaybackId(payback.getId());
            paybackPageResponse.setBorrower(payback.getBorrower());
            paybackPageResponse.setLender(payback.getLender());
            paybackPageResponse.setPaybackAmount(payback.getPaybackAmount());
            paybackPageResponse.setInterestRate(payback.getInterestRate());
            paybackPageResponse.setTotalPaybackWithInterest(payback.getPaybackAmount()+interest);
            paybackPageResponse.setPaybackStatus(payback.getPaybackStatus());
            paybackPageResponse.setPaybackDeadline(payback.getPaybackDeadline().toString());
            if(payback.getPaybackCompletedOn()!=null) {
                paybackPageResponse.setPaybackCompletedOn(payback.getPaybackCompletedOn().toString());
            }
            paybackPageResponseList.add(paybackPageResponse);
        }
        return paybackPageResponseList;
    }
}
