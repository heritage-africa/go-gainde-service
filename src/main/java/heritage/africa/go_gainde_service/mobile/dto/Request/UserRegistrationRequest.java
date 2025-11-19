package heritage.africa.go_gainde_service.mobile.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @Schema(
        description = "Nom d'utilisateur unique choisi par l'utilisateur",
        example = "babacar123",
        required = true
    )
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;



    // @Schema(
    //     description = "Adresse email de l'utilisateur (utilisé pour l'envoi des OTP)",
    //     example = "9aD2O@example.com"
    // )
    // private String email;

    @Schema(
        description = "Mot de passe de l'utilisateur (entre 6 et 20 caractères)",
        example = "passer123",
        required = true,
        minLength = 6,
        maxLength = 20
    )
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 20, message = "Le mot de passe doit contenir entre 6 et 20 caractères")
    private String password;

    @Schema(
        description = "Numéro de téléphone de l'utilisateur (utilisé pour l'envoi des OTP)",
        example = "+221773456789"
    )
    private String phoneNumber;
}
