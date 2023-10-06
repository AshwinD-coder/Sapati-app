package global.citytech.user.service.create;

import global.citytech.cash.repository.CashRepository;
import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class UserCreateServiceTest {
    @Inject
    private UserRepository userRepository;

    @Inject
    private CashRepository cashRepository;

    @Inject
    private UserCreateService userCreateService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        cashRepository = Mockito.mock(CashRepository.class);
        userCreateService = new UserCreateService(userRepository,cashRepository);
    }

    @Test
    void shouldCreateUserAndCashAccount(){
        try {
            UserCreateDto userCreateDto = new UserCreateDto(
                    "test",
                    "testT@123",
                    "test@gmail.com",
                    "98612929263",
                    "lender"
            );
            var actualResponse = userCreateService.createUserAndCashAccount(userCreateDto);
            UserCreateResponse userCreateResponse = new UserCreateResponse(
                    "test",
                    "test@gmail.com",
                    "LENDER",
                    false
            );
            Assertions.assertEquals(userCreateResponse.toString(),actualResponse.getData().toString());
        } catch (CustomException e) {
            fail(e.getErrorMessage());
        }
    }

    @Test
    void shouldThrowExceptionForEmptyOrBlankUsername(){
            UserCreateDto userCreateDto = new UserCreateDto(
                    "  ",
                    "testT@123",
                    "test@gmail.com",
                    "98612929263",
                    "lender"
            );
            CustomException exception = Assertions.assertThrows(CustomException.class,()->{userCreateService.createUserAndCashAccount(userCreateDto);});
            Assertions.assertEquals(ExceptionCode.USERNAME_EMPTY_OR_BLANK.getMessage(), exception.getErrorMessage());
    }
    @Test
    void shouldThrowExceptionForEmptyOrBlankPassword(){
            UserCreateDto userCreateDto = new UserCreateDto(
                    "test",
                    "",
                    "test@gmail.com",
                    "98612929263",
                    "lender"
            );
            CustomException exception = Assertions.assertThrows(CustomException.class,()->{userCreateService.createUserAndCashAccount(userCreateDto);});
            Assertions.assertEquals(ExceptionCode.PASSWORD_EMPTY_OR_BLANK.getMessage(), exception.getErrorMessage());
    }

    @Test
    void shouldThrowExceptionForPasswordLength(){
            UserCreateDto userCreateDto = new UserCreateDto(
                    "test",
                    "Test@1",
                    "test@gmail.com",
                    "98612929263",
                    "lender"
            );
            CustomException exception = Assertions.assertThrows(CustomException.class,()->{userCreateService.createUserAndCashAccount(userCreateDto);});
            Assertions.assertEquals(ExceptionCode.PASSWORD_LENGTH_LESS_THAN_8.getMessage(), exception.getErrorMessage());
    }
    @Test
    void shouldThrowExceptionForPasswordPattern(){
            UserCreateDto userCreateDto = new UserCreateDto(
                    "test",
                    "testable",
                    "test@gmail.com",
                    "98612929263",
                    "lender"
            );
            CustomException exception = Assertions.assertThrows(CustomException.class,()->{userCreateService.createUserAndCashAccount(userCreateDto);});
            Assertions.assertEquals(ExceptionCode.PASSWORD_PATTERN_INVALID.getMessage(), exception.getErrorMessage());
    }

    @Test
    void shouldThrowExceptionForIncorrectEmailFormat(){
            UserCreateDto userCreateDto = new UserCreateDto(
                    "test",
                    "Test@123",
                    "tes//t()@gm#12ail???@.co@m",
                    "98612929263",
                    "lender"
            );
            CustomException exception = Assertions.assertThrows(CustomException.class,()->{userCreateService.createUserAndCashAccount(userCreateDto);});
            Assertions.assertEquals(ExceptionCode.EMAIL_FORMAT_INCORRECT.getMessage(), exception.getErrorMessage());
    }

    @Test
    void shouldThrowExceptionForUserTypeNotAllowed(){
            UserCreateDto userCreateDto = new UserCreateDto(
                    "test",
                    "Test@123",
                    "test@gmail.com",
                    "98612929263",
                    "boss"
            );
            CustomException exception = Assertions.assertThrows(CustomException.class,()->{userCreateService.createUserAndCashAccount(userCreateDto);});
            Assertions.assertEquals(ExceptionCode.USER_TYPE_NOT_ALLOWED.getMessage(), exception.getErrorMessage());
    }



}
