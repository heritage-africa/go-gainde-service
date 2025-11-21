package heritage.africa.go_gainde_service.web.dto.request;

import lombok.Data;

@Data
public class ProduitFilterRequest {
    private String nomProduit; // Pour hasNomProduitLike
    private Long categorieId;    // Pour inCategorie
    private Double prixMin;    // Pour prixGreaterThanOrEqualTo
    // Vous pouvez ajouter d'autres champs de filtre ici
}