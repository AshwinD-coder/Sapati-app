package global.citytech.user.service.verifyemail;

import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.dto.UserEmailVerificationDto;
import jakarta.inject.Inject;

import java.util.Optional;

public class UserEmailVerificationService {
    @Inject
    private UserRepository userRepository;

    public UserEmailVerificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public CustomResponseHandler<String> verifyEmail(UserEmailVerificationDto userEmailVerificationDto) throws CustomException {
            Optional<User> user = this.userRepository.findByEmail(userEmailVerificationDto.getEmail());
            if (user.isPresent()) {
                if (user.get().getVerifyStatus().equals(false)) {
                    if(user.get().getOtp().equals(userEmailVerificationDto.getOtp())) {
                        user.get().setVerifyStatus(true);
                        this.userRepository.update(user.get());
                        return new CustomResponseHandler<>("0", "Success", "User Verified!");
                    }
                    else throw new CustomException(ExceptionCode.OTP_INCORRECT);
                } else throw new CustomException(ExceptionCode.EMAIL_ALREADY_VERIFIED);
            } else {
                throw new CustomException(ExceptionCode.USER_NOT_FOUND);
            }
    }
}
