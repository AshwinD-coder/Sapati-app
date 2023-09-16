package global.citytech.user.service.create;

import global.citytech.user.model.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.validation.ValidationService;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

public class UserCreateService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private ValidationService validationService;

    public static void hashPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }
    public String createUserAccount(User user) {
        validationService.validate(user);
        hashPassword(user);
        this.userRepository.save(user);
        return "User Created Successfully!";
    }
}
