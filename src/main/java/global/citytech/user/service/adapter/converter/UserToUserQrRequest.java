package global.citytech.user.service.adapter.converter;

import global.citytech.user.repository.User;
import global.citytech.user.service.qr.UserQrRequest;

public class UserToUserQrRequest {
    public static UserQrRequest toUserQrRequest(User user){
    UserQrRequest userQrRequest = new UserQrRequest();
    userQrRequest.setUsername(user.getUsername());
    return userQrRequest;
    }
}
