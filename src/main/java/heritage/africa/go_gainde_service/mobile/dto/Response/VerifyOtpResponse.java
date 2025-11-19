package heritage.africa.go_gainde_service.mobile.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyOtpResponse {
    private Long otpId;
    private String message;
    private String username;
    private String phoneNumber;
}
