package global.citytech.cash.service.balance;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CashBalanceResponse {
    private String username;
    private Integer amount;

    public CashBalanceResponse() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public CashBalanceResponse(String username, Integer amount) {
        this.username = username;
        this.amount = amount;
    }
}
