package heritage.africa.go_gainde_service.utils.exception;

import lombok.Data;

@Data
public class NotFoundException  extends RuntimeException {


    private final String code = "INTERNAL_SERVER_ERROR";

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
    
}

