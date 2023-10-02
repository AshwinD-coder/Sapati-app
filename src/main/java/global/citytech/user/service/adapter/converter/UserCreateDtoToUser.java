package global.citytech.user.service.adapter.converter;

import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.repository.User;
import global.citytech.platform.common.enums.UserType;

public class UserCreateDtoToUser {
    private UserCreateDtoToUser(){}
    public static boolean setVerifyStatus(UserCreateDto userCreateDto) {
        return UserType.valueOf(userCreateDto.getUserType()).compareTo(UserType.ADMIN) == 0;
    }

    public static User toUser(UserCreateDto userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        user.setPhoneNumber(userCreateDTO.getPhoneNumber());
        user.setEmail(userCreateDTO.getEmail());
        user.setUserType(UserType.valueOf(userCreateDTO.getUserType()));
        user.setVerifyStatus(setVerifyStatus(userCreateDTO));
        user.setBlacklistStatus(false);
        user.setActiveStatus(true);
        return user;
    }
}
