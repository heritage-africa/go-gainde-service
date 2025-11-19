package heritage.africa.go_gainde_service.utils.exception;

import lombok.Data;

@Data
public class InternalServerErrorException  extends RuntimeException {


    private final String code = "INTERNAL_SERVER_ERROR";

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
    
}


