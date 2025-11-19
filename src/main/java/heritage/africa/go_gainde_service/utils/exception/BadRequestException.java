package heritage.africa.go_gainde_service.utils.exception;

import lombok.Data;

@Data
public class BadRequestException  extends RuntimeException {


    private final String code = "FORBIDDEN";

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
    
}



