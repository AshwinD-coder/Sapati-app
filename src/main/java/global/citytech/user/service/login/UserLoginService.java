package global.citytech.user.service.login;

import global.citytech.borrow.service.expire.ExpireService;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.payback.service.blacklist.BlacklistService;
import global.citytech.payback.service.notification.NotificationService;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.security.SecurityUtils;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.converter.UserConverter;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserLoginService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private ExpireService expireService;

    @Inject
    private BlacklistService blacklistService;

    @Inject
    NotificationService notificationService;

    public UserLoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkVerifyStatus(User user) throws CustomException {
        if (user.getVerifyStatus().equals(false)) {
            throw new CustomException(ExceptionCode.USER_NOT_VERIFIED);
        }
    }

    public String authenticateAndValidateUser(UserLoginDto userLoginDto) throws CustomException {
        Optional<User> user = this.userRepository.findByUsername(userLoginDto.getUsername());
        if (user.isPresent()) {
            boolean isPasswordValid = BCrypt.checkpw(userLoginDto.getPassword(), user.get().getPassword());
            if (isPasswordValid) {
                validateUserStatus(user.get());
                return SecurityUtils.token(user.get());
            } else {
                throw new CustomException(ExceptionCode.PASSWORD_INCORRECT);
            }
        } else {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }

    }

    private void validateUserStatus(User user) throws CustomException {
        checkVerifyStatus(user);
        checkActiveStatus(user);
        checkBlacklistStatus(user);
    }

    private void checkBlacklistStatus(User user) throws CustomException {
        if (user.getBlacklistStatus().equals(true)) {
            throw new CustomException(ExceptionCode.USER_BLACKLISTED);
        }
    }

    private void checkActiveStatus(User user) throws CustomException {
        if (user.getActiveStatus().equals(false)) {
            throw new CustomException(ExceptionCode.USER_NOT_ACTIVE);
        }
    }

    public CustomResponseHandler<UserLoginResponse> loginUserAccount(UserLoginDto userLoginDTO) throws CustomException {
        String token = authenticateAndValidateUser(userLoginDTO);
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        if (user.isEmpty()) {
            throw new CustomException(ExceptionCode.USER_NOT_FOUND);
        }
        UserLoginResponse userLoginResponse = UserConverter.toUserLoginResponse(user.get(), token);
        expireService.expireMoneyRequestsAndSendMail();
        notificationService.checkForDeadline(user.get().getUsername());
        blacklistService.blacklistBorrowerIfDeadlineCrossed();
        return new CustomResponseHandler<>("0", "Login Successful", userLoginResponse);
    }
}

