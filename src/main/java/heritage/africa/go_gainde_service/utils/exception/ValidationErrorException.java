package heritage.africa.go_gainde_service.utils.exception;

import lombok.Data;

@Data
public class ValidationErrorException  extends RuntimeException {


    private final String code = "validation_error";

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
    
}
