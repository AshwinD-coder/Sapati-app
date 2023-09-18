package global.citytech.user.service.login;

import global.citytech.user.dto.UserLoginDto;
import global.citytech.user.model.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.security.JwtGenerator;
import global.citytech.user.service.validation.UserValidationService;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserLoginService {
    @Inject
    private UserRepository userRepository;

   private final JwtGenerator jwtGenerator = new JwtGenerator();

    @Inject
    UserValidationService userValidationService;

    public UserLoginService(UserRepository userRepository, UserValidationService userValidationService) {
        this.userRepository = userRepository;
        this.userValidationService = userValidationService;
    }

    public String loginUserAccount(UserLoginDto userLoginDTO) {
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        if (user.isPresent()) {
                userValidationService.checkVerifyStatus(user.get());
                boolean isPasswordValid = BCrypt.checkpw(userLoginDTO.getPassword(), user.get().getPassword());
                if (isPasswordValid) return jwtGenerator.generateToken(user.get().getUsername(),user.get().getUserType());
                else return "Invalid Password!";
            }
        else {
            return "Username not found!";
        }
    }
}

