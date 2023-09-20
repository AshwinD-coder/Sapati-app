package global.citytech.user.service.adapter.converter;

import global.citytech.user.repository.User;
import global.citytech.user.service.login.UserLoginResponse;

public class UserToUserLoginResponse {
    public static UserLoginResponse toUserLoginResponse(User user, String token){
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setUsername(user.getUsername());
        userLoginResponse.setUserType(user.getUserType());
        userLoginResponse.setToken(token);
        return userLoginResponse;
    }
}
