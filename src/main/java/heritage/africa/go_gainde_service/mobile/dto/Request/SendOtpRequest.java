package heritage.africa.go_gainde_service.mobile.dto.Request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SendOtpRequest {
    
    @Schema(
        description = "Nom d'utilisateur utilisé pour se connecter",
        example = "babacar",
        required = true
    )
    private String username;


    @Schema(
        description = "Date de naissance de l'utilisateur",
        example = "2000-01-01",
        required = true
    )
    private String dateNaissance;

    @Schema(
        description = "Numéro de téléphone de l'utilisateur (utilisé pour l'envoi des OTP)",
        example = "+221773456789"
    )
    private String phoneNumber;

    @Schema(
        description = "Longitude de l'utilisateur",
        example = "10.0"
    )
    private String longitude;

    @Schema(
        description = "Latitude de l'utilisateur",
        example = "10.0"
    )
    private String latitude;
}
