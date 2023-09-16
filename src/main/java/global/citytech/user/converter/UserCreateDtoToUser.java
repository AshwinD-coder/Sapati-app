package global.citytech.user.converter;

import global.citytech.user.dto.UserCreateDto;
import global.citytech.user.model.User;
import global.citytech.user.model.UserType;
import global.citytech.user.util.UserUtility;

public class UserCreateDtoToUser {
    public static boolean setVerifyStatus(UserCreateDto userCreateDto) {
        return userCreateDto.getUserType().compareTo(UserType.Admin) == 0;
    }

    public static User toUser(UserCreateDto userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());
        user.setPhoneNumber(userCreateDTO.getPhoneNumber());
        user.setEmail(userCreateDTO.getEmail());
        user.setUserType(userCreateDTO.getUserType());
        user.setVerifyStatus(setVerifyStatus(userCreateDTO));
        return user;
    }
}
