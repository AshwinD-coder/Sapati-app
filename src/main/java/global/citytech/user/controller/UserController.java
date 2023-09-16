package global.citytech.user.controller;


import global.citytech.user.converter.UserCreateDtoToUser;
import global.citytech.user.dto.UserCreateDto;
import global.citytech.user.dto.UserLoginDto;
import global.citytech.user.model.User;
import global.citytech.user.service.create.UserCreateService;
import global.citytech.user.service.listusers.UserListService;
import global.citytech.user.service.login.UserLoginService;
import global.citytech.user.service.verifyemail.EmailVerificationService;
import global.citytech.user.service.verifyemail.EmailVerificationRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;


@Controller("/user")
public class UserController {
    @Inject
    private UserLoginService userLoginService;
    @Inject
    private UserCreateService userCreateService;

    @Inject
    UserListService userListService;
    @Inject
    EmailVerificationService emailVerificationService;

    public UserController(UserLoginService userLoginService, UserCreateService userCreateService) {
        this.userLoginService = userLoginService;
        this.userCreateService = userCreateService;
    }

    @Get("/")
    public String index() {
        return "User Page!";
    }

    @Post("/create")
    public String createUser(@Body UserCreateDto userCreateDTO) {
        User user = UserCreateDtoToUser.toUser(userCreateDTO);
        return this.userCreateService.createUserAccount(user);
    }


    @Post("/login")
    public String loginUser(@Body UserLoginDto userLoginDTO) {
        return this.userLoginService.loginUserAccount(userLoginDTO);
    }

    @Post("/verifyEmail/{id}")
    public String verifyUserEmail(@Body EmailVerificationRequest emailVerificationRequest , @PathVariable Long id){
        return emailVerificationService.verifyEmail(emailVerificationRequest,id);
    }

    @Get("/listUser")
    public String listUsers(){
        return userListService.listUsers().toString();
    }
}

