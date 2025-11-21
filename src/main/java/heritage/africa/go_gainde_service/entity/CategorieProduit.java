package heritage.africa.go_gainde_service.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CategorieProduit extends AbstracType {

    private String nomCategorie;


    @OneToMany(mappedBy = "categoryProduit")
    private List<Produit> produits;

    
}
