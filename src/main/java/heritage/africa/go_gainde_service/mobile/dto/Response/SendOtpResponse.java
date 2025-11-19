package heritage.africa.go_gainde_service.mobile.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendOtpResponse {
    private Long otpId;
    private String message;
}
