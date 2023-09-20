package global.citytech.user.service.listusers;

import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.converter.UserToUserListResponse;
import jakarta.inject.Inject;

import java.util.List;


public class UserListService {
    @Inject
    UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomResponseHandler<List<UserListResponse>> listUsers() {
        List<User> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            throw new IllegalArgumentException("No list of users!");
        }
        List<UserListResponse> userListResponse = UserToUserListResponse.toUserListResponse(users);
        return new CustomResponseHandler<>("0", "Success", userListResponse);
    }
}
