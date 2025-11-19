package heritage.africa.go_gainde_service.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthRequest1 {



    @Schema(
        description = "email d'utilisateur utilisé pour se connecter",
        example = "babacar",
        required = true
    )
    private String email;

    @Schema(
        description = "Mot de passe associé au compte utilisateur",
        example = "123456",
        required = true
    )
    private String password;
    
}
