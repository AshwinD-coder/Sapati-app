package global.citytech.cash.service.balance;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CashBalanceRequest {
    private String username;

    public CashBalanceRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
