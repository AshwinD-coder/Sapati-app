package global.citytech.moneyrequest.repository;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;


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
    private String borrower;
    private String lender;
    private Integer amount;
    private String remarks;
    private Date returnDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;
    @DateCreated
    private Timestamp requestedAt;

    @Nullable
    @DateUpdated
    private Timestamp requestStatusUpdatedOn;


    public MoneyRequest(UUID transactionId, String borrower, String lender, Integer amount, String remarks, Date returnDate, RequestStatus requestStatus, Timestamp requestedAt, @Nullable Timestamp requestStatusUpdatedOn) {
        this.transactionId = transactionId;
        this.borrower = borrower;
        this.lender = lender;
        this.amount = amount;
        this.remarks = remarks;
        this.returnDate = returnDate;
        this.requestStatus = requestStatus;
        this.requestedAt = requestedAt;
        this.requestStatusUpdatedOn = requestStatusUpdatedOn;
    }

    public MoneyRequest() {

    }



    @Override
    public String toString() {
        return "{" + "transactionId:" + transactionId + ", requestFrom:'" + borrower + '\'' + ", requestTo:'" + lender + '\'' + ", amount:" + amount + ", remarks:'" + remarks + '\'' + ", returnDate:" + returnDate + ", requestedAt:" + requestedAt + ", requestStatus:" + requestStatus + '}';
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
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



    @Nullable
    public Timestamp getRequestStatusUpdatedOn() {
        return requestStatusUpdatedOn;
    }
    @Nullable
    public void setRequestStatusUpdatedOn(Timestamp requestStatusUpdatedOn) {
        this.requestStatusUpdatedOn = requestStatusUpdatedOn;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}

