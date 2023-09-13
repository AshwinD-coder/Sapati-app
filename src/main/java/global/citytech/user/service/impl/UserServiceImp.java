package global.citytech.user.service.impl;

import global.citytech.user.converter.UserDTOtoUser;
import global.citytech.user.dto.UserDTO;
import global.citytech.user.dto.UserLoginDTO;
import global.citytech.user.model.User;
import global.citytech.user.model.UserType;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.UserService;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserServiceImp implements UserService {
    @Inject
    private  UserRepository userRepository;
    @Inject
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User hashPassword(User user){
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
        return user;
    }

     void checkUserExistence(UserDTO userDTO){
        Optional<User> userName = this.userRepository.findByUsername(userDTO.getUsername());
        if(userName.isPresent()){
            throw new IllegalArgumentException("User already exists");
        }
    }
     void checkAdminExistence(UserDTO userDTO){
        Optional<User> userType = this.userRepository.findByUserType(userDTO.getUserType());
        if(userType.isPresent()){
            if(userType.get().getUserType().compareTo(UserType.Admin)==0){
                throw new IllegalArgumentException("Admin already exists");
            }
        }
    }
    @Override
    public String createUserAccount(UserDTO userDTO){
        checkUserExistence(userDTO);
        checkAdminExistence(userDTO);
        UserDTOtoUser userDTOtoUser = new UserDTOtoUser();
        User user = userDTOtoUser.toDTO(userDTO);
        user = hashPassword(user);
        this.userRepository.save(user);
        return "User Created Successfully!";

    }


    @Override
    public String loginUser(UserLoginDTO userLoginDTO){
        Optional<User> user = this.userRepository.findByUsername(userLoginDTO.getUsername());
        if(user.isPresent()) {
            boolean isPasswordValid = BCrypt.checkpw(userLoginDTO.getPassword(), user.get().getPassword());
            if(isPasswordValid)
                return "Login Successful: Logged In :"+user.get().getUserType();
            else {
                return "Password Invalid";
            }
        }
        else{
            return "User doesn't exists";
        }
    }
    public User getUser(Long id){
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }
}
