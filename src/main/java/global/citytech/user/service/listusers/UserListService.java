package global.citytech.user.service.listusers;

import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.platform.security.ContextHolder;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.converter.UserConverter;
import jakarta.inject.Inject;

import java.util.List;


public class UserListService {
    @Inject
    UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomResponseHandler<List<UserListResponse>> listUsers() throws CustomException {
        validateRequest(ContextHolder.get().getUserType());
        List<User> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            throw new CustomException(ExceptionCode.EMPTY_USER_LIST);
        }
        List<UserListResponse> userListResponse = UserConverter.toUserListResponse(users);
        return new CustomResponseHandler<>("0", "Success", userListResponse);
    }

    private void validateRequest(String userType) {
        if(!userType.equals(UserType.ADMIN.name())){
            throw new IllegalArgumentException("Only admin is allowed to view user list!");
        }
    }
}
