package heritage.africa.go_gainde_service.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Produit extends AbstracType {


    private String imageUrl;

    private String libelle;

    private String description;

    private double prix;

    private int quantity;

    private List<String> taille;

    private double newPrix;

    
    @ManyToOne
    @JoinColumn(name = "categorie_produit_id", nullable = false)
    private CategorieProduit categoryProduit;
    
}
