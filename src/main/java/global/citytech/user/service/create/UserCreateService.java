package global.citytech.user.service.create;

import global.citytech.user.service.adapter.converter.UserCreateDtoToUser;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.validation.UserValidationService;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

public class UserCreateService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private UserValidationService userValidationService;

    public static void hashPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }
    public String createUserAccount(UserCreateDto userCreateDTO) {
        User user = UserCreateDtoToUser.toUser(userCreateDTO);
        userValidationService.validateCreateUser(user);
        hashPassword(user);
        this.userRepository.save(user);
        return "User Created Successfully!";
    }
    public void  requestvALIDATION(){

    }
}
