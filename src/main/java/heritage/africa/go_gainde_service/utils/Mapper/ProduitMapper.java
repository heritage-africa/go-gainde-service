package heritage.africa.go_gainde_service.utils.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import heritage.africa.go_gainde_service.entity.Produit;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitOneResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitResponse;

@Mapper(componentModel = "spring")
public interface ProduitMapper {

    ProduitResponse toProduitResponse(Produit produit);

    @Mapping(target = "nomProduit", source = "libelle")
    @Mapping(target = "categorie", source = "categoryProduit.nomCategorie")
    @Mapping(target = "taille", source = "taille")
    ProduitOneResponse toProduitOneResponse(Produit produit);
    
}