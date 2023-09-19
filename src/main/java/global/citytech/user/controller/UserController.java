package global.citytech.user.controller;


import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import global.citytech.user.repository.User;
import global.citytech.user.service.create.UserCreateService;
import global.citytech.user.service.delete.UserDeleteRequest;
import global.citytech.user.service.delete.UserDeleteService;
import global.citytech.user.service.listusers.UserListService;
import global.citytech.user.service.login.UserLoginService;
import global.citytech.user.service.verifyemail.EmailVerificationService;
import global.citytech.user.service.verifyemail.EmailVerificationRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.*;

@Controller("/user")
public class UserController {
    @Inject
    private UserLoginService userLoginService;
    @Inject
    private UserCreateService userCreateService;
    @Inject
    UserListService userListService;

    @Inject
    UserDeleteService userDeleteService;
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
        return this.userCreateService.createUserAccount(userCreateDTO);
    }


    @Post("/login")
    public String loginUser(@Body UserLoginDto userLoginDTO) {
        return this.userLoginService.loginUserAccount(userLoginDTO);
    }

    @Post("/verifyEmail")
    public String verifyUserEmail(@Body EmailVerificationRequest emailVerificationRequest) {
        return emailVerificationService.verifyEmail(emailVerificationRequest);
    }

    @Get("/listUser")
    public List<User> listUsers() {
        return userListService.listUsers();
    }

    @Post("/delete")
    public HttpResponse<String> delete(@Body UserDeleteRequest userDeleteRequest){
        return userDeleteService.deleteUser(userDeleteRequest);
    }

}

