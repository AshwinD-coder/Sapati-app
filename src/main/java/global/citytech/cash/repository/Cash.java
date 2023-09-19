package global.citytech.cash.repository;

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

    public Cash(Long id, String username, Integer amount) {
        this.id = id;
        this.username = username;
        this.amount = amount;
    }

    public Cash(){

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
}
