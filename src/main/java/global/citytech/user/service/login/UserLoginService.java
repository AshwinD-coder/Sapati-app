package global.citytech.user.service.login;

import global.citytech.borrow.service.expire.ExpireService;
import global.citytech.payback.service.deadline.DeadlineService;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.security.JwtGenerator;
import global.citytech.platform.security.JwtParser;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.converter.UserToUserLoginResponse;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserLoginService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private ExpireService expireService;

    @Inject
    private DeadlineService deadlineService;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkVerifyStatus(User user) {
        if (user.getVerifyStatus().equals(false)) {
            throw new IllegalArgumentException("User not verified!Please verify through the email address!");
        }
    }

    public String authenticateAndValidateUser(UserLoginDto userLoginDto) {
        Optional<User> user = this.userRepository.findByUsername(userLoginDto.getUsername());
        if (user.isPresent()) {
            boolean isPasswordValid = BCrypt.checkpw(userLoginDto.getPassword(), user.get().getPassword());
            if (isPasswordValid) {
                validateUserStatus(user.get());
                return JwtGenerator.generateToken(user.get().getUsername(), user.get().getUserType());
            } else {
                throw new IllegalArgumentException("Invalid Password!");
            }
        } else {
            throw new IllegalArgumentException("User doesn't exist!");
        }

    }

    private void validateUserStatus(User user) {
        checkVerifyStatus(user);
        checkActiveStatus(user);
        checkBlacklistStatus(user);
    }

    private void checkBlacklistStatus(User user) {
        if (user.getBlacklistStatus().equals(true)) {
            throw new IllegalArgumentException("User is blacklisted!");
        }
    }

    private void checkActiveStatus(User user) {
        if (user.getActiveStatus().equals(false)) {
            throw new IllegalArgumentException("User is not active!");
        }
    }

    public CustomResponseHandler<UserLoginResponse> loginUserAccount(UserLoginDto userLoginDTO) {
        String token = authenticateAndValidateUser(userLoginDTO);
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        if(user.isEmpty()){
            throw new IllegalArgumentException("User not found!");
        }
        UserLoginResponse userLoginResponse = UserToUserLoginResponse.toUserLoginResponse(user.get(), token);
        JwtParser.parseToken(token);
        expireService.expireMoneyRequests();
        deadlineService.checkPaybackDeadline();
        return new CustomResponseHandler<>("0", "Login Successful", userLoginResponse);
    }
}

