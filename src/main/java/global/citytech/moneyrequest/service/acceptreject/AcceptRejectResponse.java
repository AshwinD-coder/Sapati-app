package global.citytech.moneyrequest.service.acceptreject;

import global.citytech.moneyrequest.repository.RequestStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Date;

@Introspected
@Serdeable
public class AcceptRejectResponse {
    private String borrower;
    private Integer amount;
    private RequestStatus requestStatus;

    public AcceptRejectResponse(String borrower, Integer amount, RequestStatus requestStatus) {
        this.borrower = borrower;
        this.amount = amount;
        this.requestStatus = requestStatus;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
