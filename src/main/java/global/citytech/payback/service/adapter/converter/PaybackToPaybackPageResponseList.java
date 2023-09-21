package global.citytech.payback.service.adapter.converter;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.service.paybackpage.PaybackPageResponse;

import java.util.ArrayList;
import java.util.List;

public class PaybackToPaybackPageResponseList {
    public static List<PaybackPageResponse> toPaybackPageResponseList(List<Payback> paybackList){
        List<PaybackPageResponse> paybackPageResponseList = new ArrayList<>();
        for (Payback payback:
                paybackList
             ) {
            PaybackPageResponse paybackPageResponse = new PaybackPageResponse();
            paybackPageResponse.setPaybackId(payback.getId());
            paybackPageResponse.setBorrower(payback.getBorrower());
            paybackPageResponse.setLender(payback.getLender());
            paybackPageResponse.setPaybackAmount(payback.getPaybackAmount());
            paybackPageResponse.setPaybackStatus(payback.getPaybackStatus());
            paybackPageResponse.setPaybackDeadline(payback.getPaybackDeadline().toString());
            paybackPageResponseList.add(paybackPageResponse);
        }
        return paybackPageResponseList;
    }
}
