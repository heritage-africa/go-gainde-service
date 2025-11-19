package heritage.africa.go_gainde_service.mobile.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        example = "1234",
        required = true
    )
    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$", message = "Le code doit contenir 4 chiffres")
    private String codeSecret;

    // getters & setters
}
