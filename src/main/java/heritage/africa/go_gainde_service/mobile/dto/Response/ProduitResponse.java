package heritage.africa.go_gainde_service.mobile.dto.Response;

import java.util.List;

import lombok.Data;


@Data
public class ProduitResponse {
    private String id;
    private String imageUrl;
    private String libelle;
    private double prix;
    private double newPrix;
    private List<String> taille;
}
