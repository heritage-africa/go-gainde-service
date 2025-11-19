package heritage.africa.go_gainde_service.web.dto.response;

// AuthResponse.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse1{
    private String token;
    private Long userId;
    private String email;
    private boolean verified;
}
