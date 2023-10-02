package global.citytech.user.service.create;

import global.citytech.cash.repository.Cash;
import global.citytech.cash.repository.CashRepository;
import global.citytech.platform.email.EmailConfiguration;
import global.citytech.platform.email.EmailService;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import global.citytech.platform.common.enums.UserType;
import global.citytech.user.service.adapter.converter.UserCreateDtoToUser;
import global.citytech.user.service.adapter.converter.UserToCash;
import global.citytech.user.service.adapter.converter.UserToUserCreateResponse;
import global.citytech.user.service.adapter.converter.UserToUserQrRequest;
import global.citytech.user.service.adapter.dto.UserCreateDto;
import global.citytech.user.service.qr.UserQrRequest;
import global.citytech.user.service.qr.UserQrService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.Random;

@Transactional(Transactional.TxType.REQUIRES_NEW)
public class UserCreateService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private CashRepository cashRepository;



    public CustomResponseHandler<UserCreateResponse> createUserAndCashAccount(UserCreateDto userCreateDTO) {
        validateCreateUser(userCreateDTO);
        User user = UserCreateDtoToUser.toUser(userCreateDTO);
        hashPassword(user);
        this.userRepository.save(user);
        createCashAccount(user);
        UserCreateResponse userCreateResponse = UserToUserCreateResponse.toUserCreateResponse(user);
        UserQrRequest userQrRequest = UserToUserQrRequest.toUserQrRequest(user);
        createEmailConfiguration(user);
        UserQrService.generateQR(userQrRequest);
        return new CustomResponseHandler<>("0", "User Created! Please verify your email to login.", userCreateResponse);
    }

    private void createEmailConfiguration(User user) {
        String emailVerifyURL = "http://localhost:8080/user/verify/"+user.getEmail();
        String htmlContent = "Please click this link to verify your account! <html>" +
                "<br><br><br><a href = '"+emailVerifyURL+"'>Verify User Account</a></html>";
        EmailConfiguration emailConfiguration = new EmailConfiguration(user.getEmail() ,"Verify User Account - Sapati App",htmlContent );
        EmailService.sendMail(emailConfiguration);
    }


    public void checkAdminExistence(UserCreateDto user) {
        if(user.getUserType().compareTo(UserType.ADMIN.name())==0) {
        Optional<User> userType = this.userRepository.findByUserType(UserType.ADMIN);
        if(userType.isPresent()) {
                throw new IllegalArgumentException("Admin already exists");
            }
        }
    }


    public void checkEmailExistence(UserCreateDto user) {
        Optional<User> existingUser = this.userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this email already exists!");
        }
    }

    public void validatePassword(UserCreateDto user) {
        String password = user.getPassword();
        if (password.isBlank() || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty or blank!!");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters.");
        }
        if(password.length()>15){
            throw new IllegalArgumentException("Password cannot be longer than 15 characters!");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("Password cannot contain whitespaces!");
        }
    }

    public void validateUser(UserCreateDto user) {
        Optional<User> userOptional = this.userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent() && userOptional.get().getActiveStatus().equals(true)) {
            throw new IllegalArgumentException("Username already taken!");
        }
        if (user.getUsername().isEmpty() || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or blank!");
        }
        if (user.getUsername().contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain whitespace!");
        }
        if(user.getUsername().length()>15){
            throw new IllegalArgumentException("Username cannot be longer than 15 characters!");
        }
    }

    public void validateCreateUser(UserCreateDto user) {
        validateUser(user);
        validatePassword(user);
        validateUserType(user);
        checkEmailExistence(user);
        checkAdminExistence(user);
    }

    private void validateUserType(UserCreateDto user) {
        if(!user.getUserType().equalsIgnoreCase("borrower") && !user.getUserType().equalsIgnoreCase("admin") && !user.getUserType().equalsIgnoreCase("lender")){
            throw new IllegalArgumentException("User type not allowed to create!");
        }
    }


    public static void hashPassword(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }

    public void validateCashAccount(User user) {
        Optional<Cash> cashAccount = this.cashRepository.findByUsername(user.getUsername());
        if (cashAccount.isPresent()) {
            throw new IllegalArgumentException("Cash Account Already exists for user");
        }
    }

    public void createCashAccount(User user) {
        validateCashAccount(user);
        if(!user.getUserType().equals(UserType.ADMIN)) {
            this.cashRepository.save(UserToCash.toCash(user));
        }
    }

}
