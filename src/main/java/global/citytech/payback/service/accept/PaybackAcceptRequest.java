package global.citytech.payback.service.accept;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Introspected
@Serdeable
public class PaybackAcceptRequest {
    private UUID transactionId;
    private String borrower;

    public PaybackAcceptRequest(UUID transactionId, String borrower) {
        this.transactionId = transactionId;
        this.borrower = borrower;
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
}
