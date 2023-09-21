package global.citytech.cash.repository;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;


@Introspected
@Serdeable
@Entity
@Table(name = "cash_info")
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public Cash(Long id, String username, Integer amount, UserType userType) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.userType = userType;
    }

    public Cash(){

    }

    public Cash(String username, int i) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
