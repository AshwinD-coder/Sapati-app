package global.citytech.payback.service.paybackpage;

import global.citytech.platform.common.enums.PaybackStatus;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Date;
import java.util.UUID;

@Serdeable

public class PaybackPageResponse {
    private UUID paybackId;
    private String borrower;
    private String lender;
    private PaybackStatus paybackStatus;
    private String paybackDeadline;
    private Integer paybackAmount;

    private String paybackCompletedOn;
    public PaybackPageResponse() {

    }

    public String getPaybackCompletedOn() {
        return paybackCompletedOn;
    }

    public void setPaybackCompletedOn(String paybackCompletedOn) {
        this.paybackCompletedOn = paybackCompletedOn;
    }

    public UUID getPaybackId() {
        return paybackId;
    }

    public void setPaybackId(UUID paybackId) {
        this.paybackId = paybackId;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public PaybackStatus getPaybackStatus() {
        return paybackStatus;
    }

    public void setPaybackStatus(PaybackStatus paybackStatus) {
        this.paybackStatus = paybackStatus;
    }

    public String getPaybackDeadline() {
        return paybackDeadline;
    }

    public void setPaybackDeadline(String paybackDeadline) {
        this.paybackDeadline = paybackDeadline;
    }

    public Integer getPaybackAmount() {
        return paybackAmount;
    }

    public void setPaybackAmount(Integer paybackAmount) {
        this.paybackAmount = paybackAmount;
    }

    public PaybackPageResponse(UUID paybackId, String borrower, String lender, PaybackStatus paybackStatus, String paybackDeadline, Integer paybackAmount) {
        this.paybackId = paybackId;
        this.borrower = borrower;
        this.lender = lender;
        this.paybackStatus = paybackStatus;
        this.paybackDeadline = paybackDeadline;
        this.paybackAmount = paybackAmount;
    }
}
