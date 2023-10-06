package global.citytech.borrow.service.acceptreject;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Introspected
@Serdeable
public class AcceptRejectRequest {
    private UUID transactionId;

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public AcceptRejectRequest(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
