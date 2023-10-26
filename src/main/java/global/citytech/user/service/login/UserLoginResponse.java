package global.citytech.user.service.login;

import global.citytech.platform.common.enums.UserType;
import io.micronaut.serde.annotation.Serdeable;


@Serdeable

public class UserLoginResponse {
    private String username;
    private String token;
    private UserType userType;

    public UserLoginResponse(String username, String token, UserType userType) {
        this.username = username;
        this.token = token;
        this.userType = userType;
    }

    public UserLoginResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", userType=" + userType +
                '}';
    }
}
