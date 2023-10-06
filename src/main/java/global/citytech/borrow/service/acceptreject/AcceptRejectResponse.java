package global.citytech.borrow.service.acceptreject;

import global.citytech.platform.common.enums.RequestStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class AcceptRejectResponse {
    private String borrower;
    private Integer amountBorrowed;
    private Double interestRate;

    private Double remainingAmount;
    private String paybackDeadline;
    private RequestStatus requestStatus;

    public AcceptRejectResponse(String borrower, Integer amountBorrowed, Double interestRate, Double remainingAmount, String paybackDeadline, RequestStatus requestStatus) {
        this.borrower = borrower;
        this.amountBorrowed = amountBorrowed;
        this.interestRate = interestRate;
        this.remainingAmount = remainingAmount;
        this.paybackDeadline = paybackDeadline;
        this.requestStatus = requestStatus;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Integer getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(Integer amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getPaybackDeadline() {
        return paybackDeadline;
    }

    public void setPaybackDeadline(String paybackDeadline) {
        this.paybackDeadline = paybackDeadline;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
