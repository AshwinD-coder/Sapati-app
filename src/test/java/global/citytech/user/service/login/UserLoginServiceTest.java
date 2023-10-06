package global.citytech.user.service.login;

import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.dto.UserLoginDto;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class UserLoginServiceTest {
    @Inject
    UserLoginService userLoginService;

    @Inject
    UserRepository userRepository;

//    @BeforeEach
//    void setUp() {
//        userRepository = Mockito.mock(UserRepository.class);
//        userLoginService = new UserLoginService(userRepository);
//    }

    @Test
    void shouldLoginUser(){
        try {
            UserLoginDto userLoginDto = new UserLoginDto(
                    "ashwin",
                    "Ashwin@123"
            );
            var actualResponse = userLoginService.loginUserAccount(userLoginDto);
            UserLoginResponse userLoginResponse = new UserLoginResponse(
                    "ashwin",
                    actualResponse.getData().getToken(),
                    UserType.LENDER
            );
            Assertions.assertEquals(userLoginResponse.toString(),actualResponse.getData().toString());
        } catch (CustomException e) {
            fail(e.getErrorMessage());
        }
    }

    @Test
    void shouldThrowExceptionForIncorrectPassword(){
        UserLoginDto userLoginDto = new UserLoginDto(
                "ashwin",
                "Ashwin@1234"
        );
        CustomException exception = Assertions.assertThrows(CustomException.class,()->{userLoginService.loginUserAccount(userLoginDto);});
        Assertions.assertEquals(ExceptionCode.PASSWORD_INCORRECT.getMessage(),exception.getErrorMessage());
    }
    @Test
    void shouldThrowExceptionForIncorrectUsername(){
        UserLoginDto userLoginDto = new UserLoginDto(
                "ashwin123",
                "Ashwin@123"
        );
        CustomException exception = Assertions.assertThrows(CustomException.class,()->{userLoginService.loginUserAccount(userLoginDto);});
        Assertions.assertEquals(ExceptionCode.USER_NOT_FOUND.getMessage(),exception.getErrorMessage());
    }

}
