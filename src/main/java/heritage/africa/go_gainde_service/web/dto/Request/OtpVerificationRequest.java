package heritage.africa.go_gainde_service.web.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerificationRequest {
    @NotBlank
    private String code;
    
    private Long userId;
}