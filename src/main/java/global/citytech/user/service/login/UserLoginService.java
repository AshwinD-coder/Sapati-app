package global.citytech.user.service.login;

import global.citytech.user.dto.UserLoginDto;
import global.citytech.user.model.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserLoginService {
    @Inject
    private UserRepository userRepository;

    public String loginUserAccount(UserLoginDto userLoginDTO) {
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        if (user.isPresent()) {
            if (user.get().getVerifyStatus()) {
                boolean isPasswordValid = BCrypt.checkpw(userLoginDTO.getPassword(), user.get().getPassword());
                if (isPasswordValid) return "Login Successful: Logged In :" + user.get().getUserType();
                else return "Password Invalid";
            } else {
                return "User Not Verified!";
            }
        } else {
            return "User doesn't exists!";
        }
    }
}

