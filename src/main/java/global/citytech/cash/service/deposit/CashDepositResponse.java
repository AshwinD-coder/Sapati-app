package global.citytech.cash.service.deposit;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CashDepositResponse {
    private String username;
    private Double amount;
    private String userType;

    public CashDepositResponse(String username, Double amount, String userType) {
        this.username = username;
        this.amount = amount;
        this.userType = userType;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
