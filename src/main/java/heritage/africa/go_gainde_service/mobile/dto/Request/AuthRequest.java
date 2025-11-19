package heritage.africa.go_gainde_service.mobile.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Requête de connexion")
public class AuthRequest {

    @Schema(
        description = "Nom d'utilisateur utilisé pour se connecter",
        example = "babacar",
        required = true
    )
    @NotBlank
    private String numeroTelephone;

    @Schema(
        description = "Mot de passe associé au compte utilisateur",
        example = "123456",
        required = true
    )
    @NotBlank
    private String password;

    // getters & setters
}
