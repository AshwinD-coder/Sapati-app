package global.citytech.user.service.create;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.platform.common.enums.UserType;
import global.citytech.user.service.adapter.converter.UserCreateDtoToUser;
import global.citytech.user.service.adapter.converter.UserToCash;
import global.citytech.user.service.adapter.converter.UserToUserCreateResponse;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class UserCreateService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private CashRepository cashRepository;

    public CustomResponseHandler<UserCreateResponse> createUserAndCashAccount(UserCreateDto userCreateDTO) {
        User user = UserCreateDtoToUser.toUser(userCreateDTO);
        validateCreateUser(user);
        hashPassword(user);
        this.userRepository.save(user);
        createCashAccount(user);
        UserCreateResponse userCreateResponse = UserToUserCreateResponse.toUserCreateResponse(user);
        return new CustomResponseHandler<>("0", "User Created! Please verify your email to login.", userCreateResponse);
    }


    public void checkAdminExistence() {
        Optional<User> userType = this.userRepository.findByUserType(UserType.ADMIN);
        if (userType.isPresent()) {
            throw new IllegalArgumentException("Admin already exists");
        }
    }


    public void checkEmailExistence(User user) {
        Optional<User> existingUser = this.userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this email already exists!");
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
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain whitespaces!");
        }
    }

    public void validateUsername(User user) {
        if (this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken!");
        }
        if (user.getUsername().isEmpty() || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or blank!");
        }
        if (user.getUsername().contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain whitespace!");
        }
    }

    public void validateCreateUser(User user) {
        validateUsername(user);
        validatePassword(user);
        checkEmailExistence(user);
        checkAdminExistence();
    }

    public static void hashPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }

    public void validateCashAccount(User user) {
        Optional<Cash> cashAccount = this.cashRepository.findByUsername(user.getUsername());
        if (cashAccount.isPresent()) {
            throw new IllegalArgumentException("Cash Account Already exists for user");
        }
    }

    public void createCashAccount(User user) {
        validateCashAccount(user);
        this.cashRepository.save(UserToCash.toCash(user));
    }

}
