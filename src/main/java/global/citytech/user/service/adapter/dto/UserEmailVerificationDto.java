package global.citytech.user.service.adapter.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable

public class UserEmailVerificationDto {
    private String Email;


    public UserEmailVerificationDto(String email) {
        Email = email;
    }

    public UserEmailVerificationDto() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}


