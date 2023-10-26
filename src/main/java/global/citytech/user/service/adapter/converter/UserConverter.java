package global.citytech.user.service.adapter.converter;

import global.citytech.cash.repository.Cash;
import global.citytech.platform.common.enums.UserType;
import global.citytech.user.repository.User;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.create.UserCreateResponse;
import global.citytech.user.service.listusers.UserListResponse;
import global.citytech.user.service.login.UserLoginResponse;
import global.citytech.user.service.qr.UserQrRequest;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {
    private UserConverter(){}
    public static User toUser(UserCreateDto userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        user.setPhoneNumber(userCreateDTO.getPhoneNumber());
        user.setEmail(userCreateDTO.getEmail());
        user.setUserType(UserType.valueOf(userCreateDTO.getUserType().toUpperCase()));
        user.setVerifyStatus(false);
        user.setBlacklistStatus(false);
        user.setActiveStatus(true);
        return user;
    }

    public static Cash toCash(User user){
        Cash cash = new Cash();
        cash.setAmount(0.0);
        cash.setUsername(user.getUsername());
        cash.setUserType(user.getUserType());
        return cash;
    }

    public static UserCreateResponse toUserCreateResponse(User user){
        UserCreateResponse userCreateResponse = new UserCreateResponse();
        userCreateResponse.setUsername(user.getUsername());
        userCreateResponse.setEmail(user.getEmail());
        userCreateResponse.setUserType(user.getUserType().toString());
        userCreateResponse.setVerifyStatus(user.getVerifyStatus());
        return userCreateResponse;
    }

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
            userListResponse.setBlacklistStatus(user.getBlacklistStatus());
            userListResponse.setActiveStatus(user.getActiveStatus());
            userListResponses.add(userListResponse);
        }
        return userListResponses;
    }

    public static UserLoginResponse toUserLoginResponse(User user, String token){
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setUsername(user.getUsername());
        userLoginResponse.setUserType(user.getUserType());
        userLoginResponse.setToken(token);
        return userLoginResponse;
    }

    public static UserQrRequest toUserQrRequest(User user){
        UserQrRequest userQrRequest = new UserQrRequest();
        userQrRequest.setUsername(user.getUsername());
        return userQrRequest;
    }
}
