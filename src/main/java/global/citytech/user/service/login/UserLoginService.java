package global.citytech.user.service.login;

import global.citytech.borrow.service.expire.ExpireService;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.service.adapter.converter.UserToUserLoginResponse;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.platform.security.JwtGenerator;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserLoginService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private ExpireService expireService;

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
    public CustomResponseHandler<UserLoginResponse> loginUserAccount(UserLoginDto userLoginDTO) {
        String token = authenticateUser(userLoginDTO);
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        UserLoginResponse userLoginResponse = UserToUserLoginResponse.toUserLoginResponse(user.get(),token);
        expireService.expireMoneyRequests();
        return new CustomResponseHandler<>("0","Login Successful",userLoginResponse);
    }
}

