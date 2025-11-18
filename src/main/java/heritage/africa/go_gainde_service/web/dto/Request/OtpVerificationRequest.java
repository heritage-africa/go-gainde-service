package heritage.africa.go_gainde_service.web.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpVerificationRequest {

    @Schema(
        description = "Code OTP envoyé par SMS pour la vérification utilisateur",
        example = "482913",
        required = true
    )
    @NotBlank(message = "Le code OTP est obligatoire")
    private String code;

    @Schema(
        description = "Identifiant de l'utilisateur qui tente de vérifier son compte",
        example = "12"
    )
    private Long userId;

    // getters & setters
}
