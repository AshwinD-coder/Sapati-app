package global.citytech.moneyrequest.service.requestpage;

import global.citytech.moneyrequest.repository.RequestStatus;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class RequestPageEntry {
    private String username;

    public RequestPageEntry(String username, RequestStatus requestStatus) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
