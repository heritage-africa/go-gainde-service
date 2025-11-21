package heritage.africa.go_gainde_service.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MatchRequest {
    
    // Ajoutez des validations de base pour vous assurer que les champs principaux existent
    @NotBlank(message = "La date du match est requise.")
    private String date;

    @NotBlank(message = "L'heure du match est requise.")
    private String time;



    // *** CLÉ : Ajout de @Valid pour déclencher la validation des objets imbriqués ***
    @Valid 
    @NotNull(message = "Les informations sur le stade sont requises.")
    private StadiumRequest stadium;
    
    // *** CLÉ : Ajout de @Valid pour déclencher la validation des objets imbriqués ***
    @Valid 
    @NotNull(message = "Les informations sur l'adversaire sont requises.")
    private OpponentRequest opponent;


    @Valid 
    @NotNull(message = "Les informations sur la compétition sont requises.")
    private CompetitionRequest competition;
    
    private boolean isHome;
    // private List<InjuredPlayerDto> injuredPlayers;

    // Getters & Setters
}