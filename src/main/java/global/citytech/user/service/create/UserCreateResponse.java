package global.citytech.user.service.create;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserCreateResponse {
    private String username;
    private String email;
    private UserType userType;

    private Boolean verifyStatus;


    public UserCreateResponse(String username, String email, UserType userType, Boolean verifyStatus) {
        this.username = username;
        this.email = email;
        this.userType = userType;
        this.verifyStatus = verifyStatus;

    }

    public UserCreateResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Boolean verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}
