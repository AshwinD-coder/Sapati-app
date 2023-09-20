package global.citytech.user.service.adapter.dto;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Introspected
@Serdeable
public class UserCreateDto {

    private String username;

    private String password;
    private String email;

    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public UserCreateDto(String username, String password, String email, String phoneNumber, UserType userType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
