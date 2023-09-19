package global.citytech.user.service.create;

import global.citytech.platform.CustomResponseHandler;
import global.citytech.user.repository.UserType;
import global.citytech.user.service.adapter.converter.UserCreateDtoToUser;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserCreateService {

    @Inject
    private UserRepository userRepository;

    public static void hashPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }
    public CustomResponseHandler<?> createUserAccount(UserCreateDto userCreateDTO) {
        User user = UserCreateDtoToUser.toUser(userCreateDTO);
        validateCreateUser(user);
        hashPassword(user);
        this.userRepository.save(user);
        UserCreateResponse userCreateResponse = new UserCreateResponse(user.getUsername(),user.getEmail(),user.getUserType(),user.getVerifyStatus());
        return new CustomResponseHandler<>("200","User Created! Please verify your email to login.",userCreateResponse);
    }
    public void validateUsername(User user) {
        if (this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken!");
        }
        if (user.getUsername().isEmpty() || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or blank!");
        }
    }

    public void checkAdminExistence(User user) {
        Optional<User> userType = this.userRepository.findByUserType(user.getUserType());
        if (userType.isPresent()) {
            if (userType.get().getUserType().compareTo(UserType.ADMIN) == 0) {
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
        if(password.contains(" ")){
            throw new IllegalArgumentException("Password cannot contain whitespaces!");
        }
    }

    public void checkEmailExistence(User user) {
        Optional<User> existingUser = this.userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this email already exists!");
        }
    }

    public void validateCreateUser(User user) {
        validateUsername(user);
        validatePassword(user);
        checkEmailExistence(user);
        checkAdminExistence(user);
    }

}
