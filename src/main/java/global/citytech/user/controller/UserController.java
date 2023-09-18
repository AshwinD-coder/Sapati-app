package global.citytech.user.controller;


import global.citytech.user.dto.UserCreateDto;
import global.citytech.user.dto.UserLoginDto;
import global.citytech.user.service.create.UserCreateService;
import global.citytech.user.service.listusers.UserListService;
import global.citytech.user.service.login.UserLoginService;
import global.citytech.user.service.verifyemail.EmailVerificationService;
import global.citytech.user.service.verifyemail.EmailVerificationRequest;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.util.Optional;

@Controller("/user")
@Secured(SecurityRule.IS_ANONYMOUS)
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
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String index() {
        return "User Page!";
    }

    @Post("/create")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String createUser(@Body UserCreateDto userCreateDTO) {
        return this.userCreateService.createUserAccount(userCreateDTO);
    }


    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String loginUser(@Body UserLoginDto userLoginDTO) {
        return this.userLoginService.loginUserAccount(userLoginDTO);
    }

    @Post("/verifyEmail")
    @Secured(SecurityRule.IS_ANONYMOUS)

    public String verifyUserEmail(@Body EmailVerificationRequest emailVerificationRequest) {
        return emailVerificationService.verifyEmail(emailVerificationRequest);
    }

    @Get("/listUser")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String listUsers() {
        return userListService.listUsers().toString();
    }

}

