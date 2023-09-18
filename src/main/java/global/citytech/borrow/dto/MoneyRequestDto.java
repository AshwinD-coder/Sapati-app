package global.citytech.borrow.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Date;

@Introspected
@Serdeable
public class MoneyRequestDto {
    private String requestFrom;
    private String requestTo;
    private Integer amount;
    private String remarks;

    private String returnDate;

    public MoneyRequestDto(String requestFrom, String requestTo, Integer amount, String remarks, String returnDate) {
        this.requestFrom = requestFrom;
        this.requestTo = requestTo;
        this.amount = amount;
        this.remarks = remarks;
        this.returnDate = returnDate;
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

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
