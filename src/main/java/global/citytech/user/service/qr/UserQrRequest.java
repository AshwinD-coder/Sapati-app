package global.citytech.user.service.qr;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserQrRequest {
    private String username;

    public UserQrRequest(String username) {
        this.username = username;
    }

    public UserQrRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
