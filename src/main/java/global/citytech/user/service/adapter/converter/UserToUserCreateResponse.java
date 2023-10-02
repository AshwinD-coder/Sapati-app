package global.citytech.user.service.adapter.converter;

import global.citytech.user.repository.User;
import global.citytech.user.service.create.UserCreateResponse;

public class UserToUserCreateResponse {
    private UserToUserCreateResponse(){}
    public static UserCreateResponse toUserCreateResponse(User user){
        UserCreateResponse userCreateResponse = new UserCreateResponse();
        userCreateResponse.setUsername(user.getUsername());
        userCreateResponse.setEmail(user.getEmail());
        userCreateResponse.setUserType(user.getUserType());
        userCreateResponse.setVerifyStatus(user.getVerifyStatus());
        return userCreateResponse;
    }
}
