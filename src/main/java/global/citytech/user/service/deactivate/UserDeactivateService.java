package global.citytech.user.service.deactivate;

import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.platform.security.ContextHolder;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.dto.UserDeactivateDto;
import jakarta.inject.Inject;

import java.util.Optional;

public class UserDeactivateService {
    @Inject
    private UserRepository userRepository;


    public UserDeactivateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deactivateUser(UserDeactivateDto userDeactivateDto) throws CustomException {
        validateRequest(ContextHolder.get().getUserType());
        Optional<User> user = this.userRepository.findById(userDeactivateDto.getId());
        if (user.isPresent()) {
            user.get().setActiveStatus(false);
            this.userRepository.update(user.get());
        } else throw new CustomException(ExceptionCode.USER_NOT_FOUND);

    }

    private void validateRequest(String userType) {
        if(!userType.equals(UserType.ADMIN.name())){
            throw new IllegalArgumentException("Only admin is allowed to deactivate user!");
        }
    }
}
