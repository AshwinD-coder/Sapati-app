package global.citytech.user.service.verifyemail;

import global.citytech.user.model.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class EmailVerificationService {
    @Inject
    private UserRepository userRepository;

    public EmailVerificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String verifyEmail(EmailVerificationRequest emailVerificationRequest, Long id) {
        if (this.userRepository.findByEmail(emailVerificationRequest.getEmail()).isPresent()) {
            Optional<User> user = this.userRepository.findById(id);
            if (user.isPresent()) {
                if (!user.get().getVerifyStatus()) {
                    user.get().setVerifyStatus(true);
                    this.userRepository.update(user.get());
                    return "User Email Verified!";
                } else throw new IllegalArgumentException("User Already Verified!");
            } else {
                throw new IllegalArgumentException("Cannot find user to verify!");
            }
        } else throw new IllegalArgumentException("Could not find the user with email.");
    }
}
