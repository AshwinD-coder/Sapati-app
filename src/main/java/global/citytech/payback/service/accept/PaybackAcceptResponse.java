package global.citytech.payback.service.accept;

import global.citytech.platform.common.enums.PaybackStatus;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class PaybackAcceptResponse {
    private UUID transactionId;
    private PaybackStatus paybackStatus;

    public PaybackAcceptResponse(UUID transactionId, PaybackStatus paybackStatus) {
        this.transactionId = transactionId;
        this.paybackStatus = paybackStatus;
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
}
