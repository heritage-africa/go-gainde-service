package heritage.africa.go_gainde_service.mobile.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompleteRegistrationRequest {
    @Schema(
        description = "ID de l'OTP",
        example = "1"
    )
    @NotNull(message = "L'ID de l'OTP est obligatoire")
    private Long otpId;


    @Schema(
        description = "Mot de passe de l'utilisateur (au moins 6 caractères)",
        example = "passer123"
    )
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
}
