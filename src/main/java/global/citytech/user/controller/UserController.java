package global.citytech.user.controller;


import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.adapter.dto.UserDeactivateDto;
import global.citytech.user.service.adapter.dto.UserEmailVerificationDto;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import global.citytech.user.service.create.UserCreateResponse;
import global.citytech.user.service.create.UserCreateService;
import global.citytech.user.service.deactivate.UserDeactivateService;
import global.citytech.user.service.listusers.UserListResponse;
import global.citytech.user.service.listusers.UserListService;
import global.citytech.user.service.login.UserLoginResponse;
import global.citytech.user.service.login.UserLoginService;
import global.citytech.user.service.verifyemail.UserEmailVerificationService;
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
    private UserListService userListService;
    @Inject
    private UserDeactivateService userDeactivateService;
    @Inject
    private UserEmailVerificationService userEmailVerificationService;

    @Post("/create")
    public HttpResponse<CustomResponseHandler<UserCreateResponse>> createUserAndCashAccount(@Body UserCreateDto userCreateDTO) {
        try {
            return HttpResponse.ok().body(userCreateService.createUserAndCashAccount(userCreateDTO));
        } catch (CustomException e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>(e.getErrorCode().toString(), e.getErrorMessage(), null));
        }
    }


    @Post("/login")
    public HttpResponse<CustomResponseHandler<UserLoginResponse>> loginUser(@Body UserLoginDto userLoginDTO) {
        try {
            return HttpResponse.ok().body(userLoginService.loginUserAccount(userLoginDTO));
        } catch (CustomException e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>(e.getErrorCode(), e.getErrorMessage(), null));
        }
    }

    @Post("/verify")
    public HttpResponse<CustomResponseHandler<String>> verifyUserEmail(@Body UserEmailVerificationDto userEmailVerificationDto) {
        try {
            return HttpResponse.ok().body(userEmailVerificationService.verifyEmail(userEmailVerificationDto));
        } catch (CustomException e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>(e.getErrorCode(), e.getErrorMessage(), null));
        }
    }

    @Get("/list")
    public HttpResponse<CustomResponseHandler<List<UserListResponse>>> listUsers() {
        try {
            return HttpResponse.ok().body(userListService.listUsers());
        } catch (CustomException e) {
            return HttpResponse.notFound(new CustomResponseHandler<>(e.getErrorCode(), e.getErrorMessage(), null));
        }
    }

    @Post("/deactivate")
    public HttpResponse<CustomResponseHandler<String>> deactivate(@Body UserDeactivateDto userDeactivateDto) {
        try {
            userDeactivateService.deactivateUser(userDeactivateDto);
            return HttpResponse.ok().body(new CustomResponseHandler<>("0", "SUCCESS", "User Deactivated!"));
        } catch (CustomException e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>(e.getErrorCode(), e.getErrorMessage(), null));
        }
    }

}

