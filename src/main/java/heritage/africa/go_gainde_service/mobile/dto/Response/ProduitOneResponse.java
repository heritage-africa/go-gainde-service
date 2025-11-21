package heritage.africa.go_gainde_service.mobile.dto.Response;

import java.util.List;

import lombok.Data;

@Data
public class ProduitOneResponse {
    private String id;
    private String nomProduit;
    private double prix;
    private String description;
    private String imageUrl;
    private String categorie;
    private double newPrix;
    private List<String> taille;
}
