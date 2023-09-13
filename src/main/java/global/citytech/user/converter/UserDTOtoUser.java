package global.citytech.user.converter;

import global.citytech.user.dto.UserDTO;
import global.citytech.user.model.User;
import jakarta.inject.Inject;

public class UserDTOtoUser {


    public User toDTO(UserDTO userDTO){
        User user =new User();
        user.setUserType(userDTO.getUserType());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
