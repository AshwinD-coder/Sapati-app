package global.citytech.payback.service.paybackpage;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;



@Introspected
@Serdeable
public class PaybackPageRequest {
 private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PaybackPageRequest(String username) {
        this.username = username;
    }
}
