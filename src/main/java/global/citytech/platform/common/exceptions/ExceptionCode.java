package global.citytech.platform.common.exceptions;

public enum ExceptionCode implements ErrorConstantCode {
    USER_NOT_FOUND(Bad_Request_code, "User not found!"),
    ADMIN_ALREADY_EXISTS(Bad_Request_code, "Admin already exists!"),
    USERNAME_ALREADY_TAKEN(Bad_Request_code, "Username already taken!"),
    USERNAME_EMPTY_OR_BLANK(Bad_Request_code, "Username cannot be empty or blank!"),
    USERNAME_CONTAINS_WHITESPACE(Bad_Request_code, "Username contains whitespaces!"),
    USER_EMAIL_EXISTS(Bad_Request_code, "Email already registered!"),
    EMAIL_FORMAT_INCORRECT(Bad_Request_code,"Email must be of gmail format!"),
    USER_TYPE_NOT_ALLOWED(Bad_Request_code,"User type must be admin, borrower or lender!"),
    PASSWORD_EMPTY_OR_BLANK(Bad_Request_code, "Password cannot be empty or blank!"),
    PASSWORD_LENGTH_LESS_THAN_8(Bad_Request_code, "Password less than 8 characters!"),
    PASSWORD_CONTAINS_WHITESPACE(Bad_Request_code, "Password contains whitespaces!"),
    PASSWORD_PATTERN_INVALID(Bad_Request_code,"Password must contain a lowercase,uppercase , digit and a special character!"),
    CASH_ACCOUNT_ALREADY_EXISTS(Bad_Request_code,"Cash account already exists!"),
    OTP_INCORRECT(Bad_Request_code,"OTP incorrect!"),
    EMAIL_ALREADY_VERIFIED(Bad_Request_code,"Email already verified!"),
    PASSWORD_INCORRECT(Bad_Request_code,"Password incorrect!"),
    USER_NOT_ACTIVE(Bad_Request_code,"User is not active!"),
    USER_BLACKLISTED(Bad_Request_code,"User is blacklisted!"),
    USER_NOT_VERIFIED(Bad_Request_code,"User is not verified!"),
    EMPTY_USER_LIST(Bad_Request_code,"User list is empty!")
    ;



    ;
    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
