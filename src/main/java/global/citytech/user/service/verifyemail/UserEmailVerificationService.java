package global.citytech.user.service.verifyemail;

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

    public CustomResponseHandler<String> verifyEmail(UserEmailVerificationDto userEmailVerificationDto) {
            Optional<User> user = this.userRepository.findByEmail(userEmailVerificationDto.getEmail());
            if (user.isPresent()) {
                if (user.get().getVerifyStatus().equals(false)) {
                    user.get().setVerifyStatus(true);
                    this.userRepository.update(user.get());
                    return new CustomResponseHandler<>("0","Success","Email Verified!");
                } else throw new IllegalArgumentException("User Already Verified!");
            } else {
                throw new IllegalArgumentException("Cannot find user to verify!");
            }
    }
}
