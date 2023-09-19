package global.citytech.moneyrequest.service.adapter.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class MoneyRequestDto {
    private String borrower;
    private String lender;
    private Integer amount;
    private String remarks;

    private String returnDate;

    public MoneyRequestDto(String borrower, String lender, Integer amount, String remarks, String returnDate) {
        this.borrower = borrower;
        this.lender = lender;
        this.amount = amount;
        this.remarks = remarks;
        this.returnDate = returnDate;
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

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
