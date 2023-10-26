package global.citytech.payback.service.accept;

import global.citytech.platform.common.enums.PaybackStatus;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class PaybackAcceptResponse {
    private UUID transactionId;
    private PaybackStatus paybackStatus;
    private Double totalPaybackWithInterest;
    private Double remainingAmount;
    private String paybackCompletedOn;

    public PaybackAcceptResponse(UUID transactionId, PaybackStatus paybackStatus, Double totalPaybackWithInterest, Double remainingAmount, String paybackCompletedOn) {
        this.transactionId = transactionId;
        this.paybackStatus = paybackStatus;
        this.totalPaybackWithInterest = totalPaybackWithInterest;
        this.remainingAmount = remainingAmount;
        this.paybackCompletedOn = paybackCompletedOn;
    }

    public PaybackAcceptResponse() {

    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public PaybackStatus getPaybackStatus() {
        return paybackStatus;
    }

    public void setPaybackStatus(PaybackStatus paybackStatus) {
        this.paybackStatus = paybackStatus;
    }

    public Double getTotalPaybackWithInterest() {
        return totalPaybackWithInterest;
    }

    public void setTotalPaybackWithInterest(Double totalPaybackWithInterest) {
        this.totalPaybackWithInterest = totalPaybackWithInterest;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getPaybackCompletedOn() {
        return paybackCompletedOn;
    }

    public void setPaybackCompletedOn(String paybackCompletedOn) {
        this.paybackCompletedOn = paybackCompletedOn;
    }
}
