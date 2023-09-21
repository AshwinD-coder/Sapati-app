package global.citytech.borrow.service.borrowpage;


import global.citytech.platform.common.enums.RequestStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Introspected
@Serdeable
public class BorrowPageResponse {
    private UUID transactionId;
    private String requestFrom;
    private String requestTo;
    private Integer amount;
    private String remarks;
    private String returnDate;
    private RequestStatus requestStatus;
    private String requestedAt;

    public BorrowPageResponse(UUID transactionId, String requestFrom, String requestTo, Integer amount, String remarks, String returnDate, RequestStatus requestStatus, String requestedAt) {
        this.transactionId = transactionId;
        this.requestFrom = requestFrom;
        this.requestTo = requestTo;
        this.amount = amount;
        this.remarks = remarks;
        this.returnDate = returnDate;
        this.requestStatus = requestStatus;
        this.requestedAt = requestedAt;
    }

    public BorrowPageResponse(){}

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

    public String getReturnDate() {
        return returnDate;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }
}
