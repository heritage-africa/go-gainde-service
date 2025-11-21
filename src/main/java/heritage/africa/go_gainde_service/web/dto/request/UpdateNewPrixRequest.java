// Créer un nouveau DTO : UpdateNewPrixRequest.java
package heritage.africa.go_gainde_service.web.dto.request; 

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateNewPrixRequest {
    
    @NotNull(message = "Le nouveau prix est requis.")
    @PositiveOrZero(message = "Le prix doit être positif ou nul.")
    private Double newPrix;
}