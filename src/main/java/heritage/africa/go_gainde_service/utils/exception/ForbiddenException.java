package heritage.africa.go_gainde_service.utils.exception;

import lombok.Data;

@Data
public class ForbiddenException  extends RuntimeException {


    private final String code = "FORBIDDEN";

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
    
}


