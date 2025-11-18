package heritage.africa.go_gainde_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    private String phoneNumber;

}
