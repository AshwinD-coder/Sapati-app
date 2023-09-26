package global.citytech.user.controller;


import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.adapter.dto.UserDeleteDto;
import global.citytech.user.service.adapter.dto.UserEmailVerificationDto;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import global.citytech.user.service.create.UserCreateResponse;
import global.citytech.user.service.create.UserCreateService;
import global.citytech.user.service.delete.UserDeleteService;
import global.citytech.user.service.listusers.UserListResponse;
import global.citytech.user.service.listusers.UserListService;
import global.citytech.user.service.login.UserLoginResponse;
import global.citytech.user.service.login.UserLoginService;
import global.citytech.user.service.verifyemail.UserEmailVerificationService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.util.List;

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

    @Get("/")
    public String index() {
        return "User Page!!";
    }

    @Post("/create")
    public HttpResponse<CustomResponseHandler<UserCreateResponse>> createUserAndCashAccount(@Body UserCreateDto userCreateDTO) {
        try {
            return HttpResponse.ok().body(userCreateService.createUserAndCashAccount(userCreateDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }


    @Post("/login")
    public HttpResponse<CustomResponseHandler<UserLoginResponse>> loginUser(@Body UserLoginDto userLoginDTO) {
        try {
            return HttpResponse.ok().body(userLoginService.loginUserAccount(userLoginDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Post("/verify")
    public HttpResponse<CustomResponseHandler<String>> verifyUserEmail(@Body UserEmailVerificationDto userEmailVerificationDto) {
        try {
            return HttpResponse.ok().body(userEmailVerificationService.verifyEmail(userEmailVerificationDto));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Get("/list")
    public HttpResponse<CustomResponseHandler<List<UserListResponse>>> listUsers() {
        try {
            return HttpResponse.ok().body(userListService.listUsers());
        } catch (Exception e) {
            return HttpResponse.notFound(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Post("/delete")
    public HttpResponse<?> delete(@Body UserDeleteDto userDeleteDto) {
        try {
            userDeleteService.deleteUser(userDeleteDto);
            return HttpResponse.ok().body(new CustomResponseHandler<>("0", "User Deletion Success", null));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(e.getMessage());
        }
    }

}

