package global.citytech.user.service;

import global.citytech.user.dto.UserDTO;
import global.citytech.user.dto.UserLoginDTO;

public interface UserService {
    String createUserAccount(UserDTO userDTO);
    String loginUser(UserLoginDTO userLoginDTO);
}
