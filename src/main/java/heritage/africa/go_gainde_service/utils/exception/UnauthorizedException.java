package heritage.africa.go_gainde_service.utils.exception;

import lombok.Data;

@Data
public class UnauthorizedException  extends RuntimeException {


    private final String code = "UNAUTHORIZED";

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
    
}
