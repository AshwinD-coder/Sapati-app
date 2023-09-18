package global.citytech.borrow.model;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


@Introspected
@Serdeable
@Entity
@Table(name = "money_requests")
public class MoneyRequest {
    @Id
    @AutoPopulated
    private UUID transactionId;

    private String requestFrom;
    private String requestTo;

    @Max(value = 50000)
    private Integer amount;
    private String remarks;

    private Date returnDate;

    @DateCreated
    private Timestamp requestedAt;

    private RequestStatus requestStatus;

    public MoneyRequest(UUID transactionId, String requestFrom, String requestTo, Integer amount, String remarks, Date returnDate, Timestamp requestedAt, RequestStatus requestStatus) {
        this.transactionId = transactionId;
        this.requestFrom = requestFrom;
        this.requestTo = requestTo;
        this.amount = amount;
        this.remarks = remarks;
        this.returnDate = returnDate;
        this.requestedAt = requestedAt;
        this.requestStatus = requestStatus;
    }

    public MoneyRequest() {

    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(String requestTo) {
        this.requestTo = requestTo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Enumerated(EnumType.STRING)
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
