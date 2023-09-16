package global.citytech.user.service.validation;

import global.citytech.user.model.User;
import global.citytech.user.model.UserType;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class ValidationService {
    @Inject
    private UserRepository userRepository;

    public void validateUsername(User user) {
        if (this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        if (user.getUsername().isEmpty() || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or blank!");
        }
    }

    public void checkAdminExistence(User user) {
        Optional<User> userType = this.userRepository.findByUserType(user.getUserType());
        if (userType.isPresent()) {
            if (userType.get().getUserType().compareTo(UserType.Admin) == 0) {
                throw new IllegalArgumentException("Admin already exists");
            }
        }
    }

    public void validatePassword(User user) {
        String password = user.getPassword();
        if (password.isBlank() || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty or blank!!");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters.");
        }

    }

    public void checkEmailExistence(User user) {
        Optional<User> existingUser = this.userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent() && existingUser.get().getVerifyStatus()) {
            throw new IllegalArgumentException("User with this email already exists!");
        }
    }

    public void validate(User user) {
        validateUsername(user);
        validatePassword(user);
        checkEmailExistence(user);
        checkAdminExistence(user);

    }

}
