package global.citytech.payback.repository;


import global.citytech.platform.common.enums.PaybackStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Introspected
@Serdeable
@Entity
@Table(name = "payback_info")
public class Payback {
    @Id
    private UUID id;
    private String borrower;
    private String lender;
    private Integer paybackAmount;

    @Enumerated(EnumType.STRING)
    private PaybackStatus paybackStatus;
    private Date paybackDeadline;

    public Payback(UUID id, String borrower, String lender, Integer paybackAmount, PaybackStatus paybackStatus, Date paybackDeadline) {
        this.id = id;
        this.borrower = borrower;
        this.lender = lender;
        this.paybackAmount = paybackAmount;
        this.paybackStatus = paybackStatus;
        this.paybackDeadline = paybackDeadline;
    }

    public Payback() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Integer getPaybackAmount() {
        return paybackAmount;
    }

    public void setPaybackAmount(Integer paybackAmount) {
        this.paybackAmount = paybackAmount;
    }

    public PaybackStatus getPaybackStatus() {
        return paybackStatus;
    }

    public void setPaybackStatus(PaybackStatus paybackStatus) {
        this.paybackStatus = paybackStatus;
    }

    public Date getPaybackDeadline() {
        return paybackDeadline;
    }

    public void setPaybackDeadline(Date paybackDeadline) {
        this.paybackDeadline = paybackDeadline;
    }
}
