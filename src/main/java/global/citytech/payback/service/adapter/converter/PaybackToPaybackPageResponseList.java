package global.citytech.payback.service.adapter.converter;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.service.interest.InterestService;
import global.citytech.payback.service.paybackpage.PaybackPageResponse;

import java.util.ArrayList;
import java.util.List;

public class PaybackToPaybackPageResponseList {

    private PaybackToPaybackPageResponseList() {
    }

    public static PaybackPageResponse toPaybackPageResponseList(Payback payback,Double interest) {
            PaybackPageResponse paybackPageResponse = new PaybackPageResponse();
            paybackPageResponse.setPaybackId(payback.getId());
            paybackPageResponse.setBorrower(payback.getBorrower());
            paybackPageResponse.setLender(payback.getLender());
            paybackPageResponse.setPaybackAmount(payback.getPaybackAmount());
            paybackPageResponse.setInterestRate(payback.getInterestRate());
            paybackPageResponse.setPaybackStatus(payback.getPaybackStatus());
            paybackPageResponse.setTotalPaybackWithInterest(payback.getPaybackAmount() + interest);
            paybackPageResponse.setPaybackDeadline(payback.getPaybackDeadline().toString());
            if (payback.getPaybackCompletedOn() != null) {
                paybackPageResponse.setPaybackCompletedOn(payback.getPaybackCompletedOn().toString());
            }
        return paybackPageResponse;
    }    public static List<PaybackPageResponse> toPaybackPageResponseList(List<Payback> paybackList) {
        List<PaybackPageResponse> paybackPageResponseList = new ArrayList<>();
        for (Payback payback :
                paybackList
        ) {
            PaybackPageResponse paybackPageResponse = new PaybackPageResponse();
            paybackPageResponse.setPaybackId(payback.getId());
            paybackPageResponse.setBorrower(payback.getBorrower());
            paybackPageResponse.setLender(payback.getLender());
            paybackPageResponse.setPaybackAmount(payback.getPaybackAmount());
            paybackPageResponse.setInterestRate(payback.getInterestRate());
            paybackPageResponse.setPaybackStatus(payback.getPaybackStatus());
            paybackPageResponse.setTotalPaybackWithInterest(payback.getTotalPaybackAmountWithInterest());
            paybackPageResponse.setPaybackDeadline(payback.getPaybackDeadline().toString());
            if (payback.getPaybackCompletedOn() != null) {
                paybackPageResponse.setPaybackCompletedOn(payback.getPaybackCompletedOn().toString());
            }
            paybackPageResponseList.add(paybackPageResponse);
        }
        return paybackPageResponseList;
    }
}
