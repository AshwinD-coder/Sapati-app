package global.citytech.user.service.verifyemail;

import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class EmailVerificationService {
    @Inject
    private UserRepository userRepository;

    public EmailVerificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String verifyEmail(EmailVerificationRequest emailVerificationRequest) {
            Optional<User> user = this.userRepository.findByEmail(emailVerificationRequest.getEmail());
            if (user.isPresent()) {
                if (!user.get().getVerifyStatus()) {
                    user.get().setVerifyStatus(true);
                    this.userRepository.update(user.get());
                    return "Email Verified!";
                } else throw new IllegalArgumentException("User Already Verified!");
            } else {
                throw new IllegalArgumentException("Cannot find user to verify!");
            }
    }
}
