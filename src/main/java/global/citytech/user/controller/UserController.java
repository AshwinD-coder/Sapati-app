package global.citytech.user.controller;


import global.citytech.platform.CustomResponseHandler;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import global.citytech.user.repository.User;
import global.citytech.user.service.create.UserCreateService;
import global.citytech.user.service.delete.UserDeleteRequest;
import global.citytech.user.service.delete.UserDeleteService;
import global.citytech.user.service.listusers.UserListResponse;
import global.citytech.user.service.listusers.UserListService;
import global.citytech.user.service.login.UserLoginService;
import global.citytech.user.service.verifyemail.UserEmailVerificationService;
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
    private UserListService userListService;

    @Inject
    private UserDeleteService userDeleteService;
    @Inject
    private UserEmailVerificationService userEmailVerificationService;

    public UserController(UserLoginService userLoginService, UserCreateService userCreateService) {
        this.userLoginService = userLoginService;
        this.userCreateService = userCreateService;
    }

    @Get("/")
    public String index() {
        return "User Page!";
    }

    @Post("/create")
    public HttpResponse<?> createUser(@Body UserCreateDto userCreateDTO) {
        try {
            return HttpResponse.ok().body(this.userCreateService.createUserAccount(userCreateDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("400",e.getMessage(),null));
        }
    }


    @Post("/login")
    public HttpResponse<?> loginUser(@Body UserLoginDto userLoginDTO) {
        try {
            return HttpResponse.ok().body(this.userLoginService.loginUserAccount(userLoginDTO));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("400",e.getMessage(),null));
        }
    }

    @Post("/verifyEmail")
    public HttpResponse<?> verifyUserEmail(@Body EmailVerificationRequest emailVerificationRequest) {
        try{
            return HttpResponse.ok().body(userEmailVerificationService.verifyEmail(emailVerificationRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("400",e.getMessage(),null));
        }
    }

    @Get("/listUser")
    public HttpResponse<?> listUsers() {
        try{
            return HttpResponse.ok().body(userListService.listUsers());
        }
        catch (Exception e){
            return  HttpResponse.notFound(e.getMessage());
        }
    }

    @Post("/delete")
    public HttpResponse<String> delete(@Body UserDeleteRequest userDeleteRequest){
        return userDeleteService.deleteUser(userDeleteRequest);
    }

}

