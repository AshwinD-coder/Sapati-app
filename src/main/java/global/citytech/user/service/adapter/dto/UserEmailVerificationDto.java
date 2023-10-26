package global.citytech.user.service.adapter.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable

public class UserEmailVerificationDto {
    private String Email;

    private String otp;

    public UserEmailVerificationDto(String email, String otp) {
        Email = email;
        this.otp = otp;
    }

    public UserEmailVerificationDto() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}


