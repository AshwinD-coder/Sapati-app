package global.citytech.user.service.login;

import global.citytech.platform.CustomResponseHandler;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.security.JwtGenerator;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserLoginService {
    @Inject
    private UserRepository userRepository;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkVerifyStatus(User user) {
        if (!user.getVerifyStatus()) {
            throw new IllegalArgumentException("User not verified!Please verify through the email address!");
        }
    }

    public String authenticateUser(UserLoginDto userLoginDto ){
        Optional<User> user = this.userRepository.findByUsername(userLoginDto.getUsername());
        if (user.isPresent()) {
            boolean isPasswordValid = BCrypt.checkpw(userLoginDto.getPassword(), user.get().getPassword());
            if (isPasswordValid) {
                checkVerifyStatus(user.get());
                return JwtGenerator.generateToken(user.get().getUsername(),user.get().getUserType());
            } else {
                throw new IllegalArgumentException("Invalid Password!");
            }
        } else {
            throw new IllegalArgumentException("User doesn't exist!");
        }

    }
    public CustomResponseHandler<?> loginUserAccount(UserLoginDto userLoginDTO) {
        String token = authenticateUser(userLoginDTO);
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        Map<String,Object> data = new HashMap<>();
        data.put("username",userLoginDTO.getUsername());
        data.put("accessToken",token);
        data.put("userType",user.get().getUserType());
        return new CustomResponseHandler<>("200","Login Successful",data);
    }
}

