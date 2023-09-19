package global.citytech.user.service.listusers;

import global.citytech.platform.CustomResponseHandler;
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

    public  CustomResponseHandler<?> listUsers(){
        List<User> users = this.userRepository.findAll();
        if(users.isEmpty()){
            throw new IllegalArgumentException("No list of users!");
        }
        return new CustomResponseHandler<>("200","Success", UserToUserListResponse.toUserListResponse(users));
   }
}
