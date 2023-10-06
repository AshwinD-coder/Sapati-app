package global.citytech.user.service.create;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.platform.common.email.EmailConfiguration;
import global.citytech.platform.common.email.EmailSender;
import global.citytech.platform.common.enums.UserType;
import global.citytech.platform.common.exceptions.CustomException;
import global.citytech.platform.common.exceptions.ExceptionCode;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.user.service.adapter.converter.UserConverter;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.qr.UserQrRequest;
import global.citytech.user.service.qr.UserQrService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.RandomStringUtils.*;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class UserCreateService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private CashRepository cashRepository;

    public UserCreateService(UserRepository userRepository, CashRepository cashRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
    }

    public CustomResponseHandler<UserCreateResponse> createUserAndCashAccount(UserCreateDto userCreateDTO) throws CustomException {
        validateCreateUser(userCreateDTO);
        User user = UserConverter.toUser(userCreateDTO);
        hashPassword(user);
        generateOTP(user);
        this.userRepository.save(user);
        createCashAccount(user);
        UserCreateResponse userCreateResponse = UserConverter.toUserCreateResponse(user);
        UserQrRequest userQrRequest = UserConverter.toUserQrRequest(user);
        userCreateEmailConfiguration(user);
        UserQrService.generateQR(userQrRequest);
        return new CustomResponseHandler<>("0", "User Created! Please verify your email to login.", userCreateResponse);
    }

    private void generateOTP(User user) {
        String numbers = "0123456789";
        user.setOtp(random(5,numbers));
    }

    private void userCreateEmailConfiguration(User user) {
        String htmlContent = """
                <html>
                Your OTP is : [otp]
                </html>
                """;
        EmailConfiguration emailConfiguration = new EmailConfiguration(user.getEmail() ,"OTP Email Verification - Sapati App",htmlContent.replace("[otp]", user.getOtp()) );
        EmailSender.sendMail(emailConfiguration);
    }

    private void validatePassword(UserCreateDto userCreateDto) throws CustomException {
        String password = userCreateDto.getPassword();
        Pattern lowerCasePattern = Pattern.compile("[a-z ]");
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Pattern numberPattern = Pattern.compile("[0-9 ]");
        Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9 ]");
        if (password.isBlank() || password.isEmpty()) {
            throw new CustomException(ExceptionCode.PASSWORD_EMPTY_OR_BLANK);
        }
        if (password.length() < 8) {
            throw new CustomException(ExceptionCode.PASSWORD_LENGTH_LESS_THAN_8);
        }
        if (password.contains(" ")) {
            throw new CustomException(ExceptionCode.PASSWORD_CONTAINS_WHITESPACE);
        }
        if (!lowerCasePattern.matcher(password).find()) {
            throw new CustomException(ExceptionCode.PASSWORD_PATTERN_INVALID);
        }
        if (!upperCasePattern.matcher(password).find()) {
            throw new CustomException(ExceptionCode.PASSWORD_PATTERN_INVALID);
        }
        if (!numberPattern.matcher(password).find()) {
            throw new CustomException(ExceptionCode.PASSWORD_PATTERN_INVALID);
        }
        if (!specialCharacterPattern.matcher(password).find()) {
            throw new CustomException(ExceptionCode.PASSWORD_PATTERN_INVALID);
        }
    }

    private void validateUser(UserCreateDto userCreateDto) throws CustomException {
        Optional<User> userOptional = this.userRepository.findByUsername(userCreateDto.getUsername());
        Optional<User> admin = this.userRepository.findByUserType(UserType.ADMIN);
        if (userOptional.isPresent() && userOptional.get().getActiveStatus().equals(true)) {
            throw new CustomException(ExceptionCode.USERNAME_ALREADY_TAKEN);
        }
        if (userCreateDto.getUsername().isEmpty() || userCreateDto.getUsername().isBlank()) {
            throw new CustomException(ExceptionCode.USERNAME_EMPTY_OR_BLANK);
        }
        if (userCreateDto.getUsername().contains(" ")) {
            throw new CustomException(ExceptionCode.USERNAME_CONTAINS_WHITESPACE);
        }
        if (admin.isPresent() && userCreateDto.getUserType().equalsIgnoreCase("admin")) {
            throw new CustomException(ExceptionCode.ADMIN_ALREADY_EXISTS);
        }
    }

    private void validateCreateUser(UserCreateDto userCreateDto) throws CustomException {
        validateUser(userCreateDto);
        validatePassword(userCreateDto);
        validateUserType(userCreateDto);
        validateEmail(userCreateDto);
    }

    private void validateEmail(UserCreateDto userCreateDto) throws CustomException {
        Optional<User> user = this.userRepository.findByEmail(userCreateDto.getEmail());
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9-.]*@gmail[.]com");
        Matcher matcher = pattern.matcher(userCreateDto.getEmail());
        Boolean isValidEmail = matcher.matches();
        if (isValidEmail.equals(false)) {
            throw new CustomException(ExceptionCode.EMAIL_FORMAT_INCORRECT);
        }
        if (user.isPresent()) {
            throw new CustomException(ExceptionCode.USER_EMAIL_EXISTS);
        }

    }

    private void validateUserType(UserCreateDto userCreateDto) throws CustomException {
        if (!userCreateDto.getUserType().equalsIgnoreCase("admin") && !userCreateDto
                .getUserType().equalsIgnoreCase("borrower") && !userCreateDto.getUserType().equalsIgnoreCase("lender")) {
            throw new CustomException(ExceptionCode.USER_TYPE_NOT_ALLOWED);
        }
    }


    private static void hashPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }

    private void validateCashAccount(User user) throws CustomException {
        Optional<Cash> cashAccount = this.cashRepository.findByUsername(user.getUsername());
        if (cashAccount.isPresent()) {
            throw new CustomException(ExceptionCode.CASH_ACCOUNT_ALREADY_EXISTS);
        }
    }

    private void createCashAccount(User user) throws CustomException {
        validateCashAccount(user);
        if (!user.getUserType().equals(UserType.ADMIN)) {
            this.cashRepository.save(UserConverter.toCash(user));
        }
    }

}
