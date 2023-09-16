package global.citytech.user.service.verifyemail;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected

public class EmailVerificationRequest {
    private String Email;


    public EmailVerificationRequest(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}


