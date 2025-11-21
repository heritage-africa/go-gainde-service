package heritage.africa.go_gainde_service.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import heritage.africa.go_gainde_service.entity.CategorieProduit;
import heritage.africa.go_gainde_service.entity.Produit; // Assurez-vous d'importer l'entité CategorieProduit
import jakarta.persistence.criteria.Join;

public class ProduitSpecification {

    // --- Spécifications d'Égalité (GET by ID) ---

    /**
     * Recherche un produit par son ID exact.
     */
    public static Specification<Produit> hasId(Long produitId) {
        if (produitId == null) return null;
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("id"), produitId);
    }

    // --- Spécification de Recherche Textuelle (LIKE) ---

    /**
     * Recherche les produits dont le libellé contient la chaîne donnée (recherche partielle et insensible à la casse).
     */
    public static Specification<Produit> hasNomProduitLike(String nomProduit) {
        if (nomProduit == null || nomProduit.trim().isEmpty()) return null;
        
        // Pattern LIKE : %texte_recherche%
        String likePattern = "%" + nomProduit.toLowerCase() + "%";
        
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(criteriaBuilder.lower(root.get("libelle")), likePattern);
    }
    
    // --- Spécification de Jointure (GET by Catégorie) ---

    /**
     * Recherche les produits appartenant à une catégorie spécifique par l'ID de la catégorie.
     */
    public static Specification<Produit> inCategorie(Long categorieId) {
        if (categorieId == null) return null;

        return (root, query, criteriaBuilder) -> {
            // Utiliser 'categoryProduit' qui est le nom du champ de relation dans l'entité Produit
            Join<Produit, CategorieProduit> categorieJoin = root.join("categoryProduit");
            
            // Chercher l'ID dans l'entité jointe (CategorieProduit)
            return criteriaBuilder.equal(categorieJoin.get("id"), categorieId);
        };
    }
    
    // --- Spécifications de Prix / Quantité (Exemples) ---
    
    /**
     * Recherche les produits dont le prix est supérieur ou égal au prix minimum.
     */
    public static Specification<Produit> prixGreaterThanOrEqualTo(Double prixMin) {
        if (prixMin == null) return null;
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThanOrEqualTo(root.get("prix"), prixMin);
    }
}