package global.citytech.user.service.create;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UserCreateResponse {
    private String username;
    private String email;
    private String userType;

    private Boolean verifyStatus;


    public UserCreateResponse(String username, String email, String userType, Boolean verifyStatus) {
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Boolean verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    @Override
    public String toString() {
        return "UserCreateResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", verifyStatus=" + verifyStatus +
                '}';
    }
}
