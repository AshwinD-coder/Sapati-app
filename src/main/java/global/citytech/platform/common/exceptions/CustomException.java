package global.citytech.platform.common.exceptions;

public class CustomException extends Throwable{
    private final String errorCode;
    private final String errorMessage;

    public CustomException(ExceptionCode code) {
        this.errorCode = code.getCode();
        this.errorMessage = code.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
