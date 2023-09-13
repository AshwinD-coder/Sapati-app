package global.citytech.user.controller;


import global.citytech.user.dto.UserDTO;
import global.citytech.user.dto.UserLoginDTO;
import global.citytech.user.model.User;
import global.citytech.user.service.impl.UserServiceImp;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;



@Controller("/user")
public class UserController {
    @Inject
    private  UserServiceImp userUseCase;
    public UserController(UserServiceImp userUseCase) {

        this.userUseCase = userUseCase;
    }

    @Get("/")
    public String index() {
        return "User Page!";
    }

    @Post("/create")
    public String createUser(@Body UserDTO userDTO){
        return userUseCase.createUserAccount(userDTO);
    }

    @Get("/read/{id}")
    public User readUser(@PathVariable Long id){
       return userUseCase.getUser(id);
    }

    @Post("/login")
    public String loginUser(@Body UserLoginDTO userLoginDTO){
       return userUseCase.loginUser(userLoginDTO);
    }


//    @Get("/read")
//    public Iterable<User> readUsers() {
//        if (this.userRepository.findAll().isEmpty()) {
//            return ;
//        }
//        else {
//            return this.userRepository.findAll();
//        }
//    }

//    @Post("/update")
//    public String updateUser(@Body User user) {
//        return "update";
//    }

//    @Post("/Delete")
//    public String deleteUser(@Body User user){
//        Long id = user.getUserId();
//        Optional<User> userEntity = this.userRepository.findById(id);
//        if(userEntity.isPresent()) {
//            this.userRepository.deleteById(id);
//            return user.toString() + " Deleted Successfully.";
//        }
//        else{
//            return "Cannot find given user.";
//        }
//    }


}

