package heritage.africa.go_gainde_service.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionRequest {
    
    // Permet de lier à une compétition existante ou de créer une nouvelle si l'ID est omis (selon la logique du service)
    private Long id; 
    
    @NotBlank(message = "Le nom de la compétition est requis.")
    private String name;
    
    private String fullName;
    private String logo;
}