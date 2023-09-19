package global.citytech.user.service.adapter.converter;

import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserType;

public class UserCreateDtoToUser {
    public static boolean setVerifyStatus(UserCreateDto userCreateDto) {
        return userCreateDto.getUserType().compareTo(UserType.ADMIN) == 0;
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
