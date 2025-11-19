package heritage.africa.go_gainde_service.mobile.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
        description = "Mot de passe associé au compte utilisateur",
        example = "1234",
        required = true
    )
    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$", message = "Le code doit contenir 4 chiffres")
    private String codeSecret;
    
}
