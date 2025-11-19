package heritage.africa.go_gainde_service.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse1 {

    @Schema(
        description = "Jeton JWT généré après authentification réussie",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWJhY2FyIiwiZXhwIjoxNz..."
    )
    private String token;

    @Schema(
        description = "Identifiant unique de l'utilisateur",
        example = "5"
    )
    private Long userId;

    @Schema(
        description = "Adresse e-mail associée au compte utilisateur",
        example = "babacar@gmail.com"
    )
    private String email;

    @Schema(
        description = "Indique si l'utilisateur a vérifié son adresse e-mail ou son numéro",
        example = "true"
    )
    private boolean verified;
}
