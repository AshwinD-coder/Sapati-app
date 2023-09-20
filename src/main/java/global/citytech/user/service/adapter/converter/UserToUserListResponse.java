package global.citytech.user.service.adapter.converter;

import global.citytech.user.repository.User;
import global.citytech.user.service.listusers.UserListResponse;

import java.util.ArrayList;
import java.util.List;

public class UserToUserListResponse {

    public static List<UserListResponse> toUserListResponse(List<User> users){
        List<UserListResponse> userListResponses = new ArrayList<>();
        for (User user:users
             ) {
            UserListResponse userListResponse = new UserListResponse();
            userListResponse.setId(user.getUserId());
            userListResponse.setUsername(user.getUsername());
            userListResponse.setEmail(user.getEmail());
            userListResponse.setPhoneNumber(user.getPhoneNumber());
            userListResponse.setUserType(user.getUserType());
            userListResponse.setCreatedAt(user.getCreatedAt().toString());
            userListResponse.setVerifyStatus(user.getVerifyStatus());
            userListResponses.add(userListResponse);
        }
        return userListResponses;
    }
}
