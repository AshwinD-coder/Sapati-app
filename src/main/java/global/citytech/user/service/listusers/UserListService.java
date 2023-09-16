package global.citytech.user.service.listusers;

import global.citytech.user.model.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;


public class UserListService {
    @Inject
     UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  List<User> listUsers(){
        return new ArrayList<>(this.userRepository.findAll());
   }
}
