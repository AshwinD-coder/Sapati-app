package global.citytech.cash.service.balance;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CashBalanceResponse {
    private String username;
    private Double amount;

    public CashBalanceResponse() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CashBalanceResponse(String username, Double amount) {
        this.username = username;
        this.amount = amount;
    }
}
