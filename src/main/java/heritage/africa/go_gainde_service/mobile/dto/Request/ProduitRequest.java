package heritage.africa.go_gainde_service.mobile.dto.Request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProduitRequest {
    
    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;
    
    @NotBlank(message = "La description est obligatoire")
    private String description;
    
    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    private Double prix;
    
    @NotNull(message = "La taille est obligatoire")
    private String taille;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantity;
    
    @NotNull(message = "La catégorie est obligatoire")
    private Long categorieProduitId;
    
    // L'image sera envoyée via MultipartFile
    private MultipartFile image;
}
