package heritage.africa.go_gainde_service.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heritage.africa.go_gainde_service.entity.CategorieProduit;
import heritage.africa.go_gainde_service.entity.Produit;
import heritage.africa.go_gainde_service.entity.enums.TailleProduit;
import heritage.africa.go_gainde_service.mobile.dto.Request.ProduitRequest;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitOneResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitResponse;
import heritage.africa.go_gainde_service.repository.CategorieProduitRepository;
import heritage.africa.go_gainde_service.repository.ProduitRepository;
import heritage.africa.go_gainde_service.repository.specification.ProduitSpecification;
import heritage.africa.go_gainde_service.service.CloudinaryService;
import heritage.africa.go_gainde_service.service.ProduitService;
import heritage.africa.go_gainde_service.utils.Mapper.ProduitMapper;
import heritage.africa.go_gainde_service.web.dto.request.ProduitFilterRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieProduitRepository categorieProduitRepository;
    private final CloudinaryService cloudinaryService;
    private final ProduitMapper produitMapper;

    /**
     * Crée un nouveau produit avec upload d'image sur Cloudinary
     */
    @Transactional
    public ProduitOneResponse creerProduit(ProduitRequest request) {
        // Validation de la catégorie
        CategorieProduit categorie = categorieProduitRepository.findById(request.getCategorieProduitId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        // Upload de l'image sur Cloudinary
        String imageUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(request.getImage());
        }

        // taille produit
        String tailleStr = request.getTaille();
        // Vous pouvez ajouter une validation supplémentaire ici si nécessaire

        // Conversion de la taille en enum
        TailleProduit taille = null;
        if (tailleStr != null) {
            taille = TailleProduit.valueOf(tailleStr);
        }

        // Création du produit
        Produit produit = new Produit();
        produit.setLibelle(request.getLibelle());
        produit.setDescription(request.getDescription());
        produit.setPrix(request.getPrix());
        produit.setQuantity(request.getQuantity());
        produit.setCategoryProduit(categorie);
        produit.setImageUrl(imageUrl);

        // Sauvegarde
        Produit savedProduit = produitRepository.save(produit);

        // Retour du DTO
        return produitMapper.toProduitOneResponse(savedProduit);
    }

    /**
     * Liste tous les produits (vue simplifiée)
     */
    @Transactional(readOnly = true)
    public List<ProduitResponse> listerProduits() {
        return produitRepository.findAll()
                .stream()
                .map(produitMapper::toProduitResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère le détail complet d'un produit
     */
    @Transactional(readOnly = true)
    public ProduitOneResponse getDetailProduit(Long produitId) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + produitId));

        return produitMapper.toProduitOneResponse(produit);
    }

    /**
     * Met à jour un produit existant (optionnel)
     */
    @Transactional
    public ProduitOneResponse updateProduit(Long produitId, ProduitRequest request) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // Mise à jour des champs
        produit.setLibelle(request.getLibelle());
        produit.setDescription(request.getDescription());
        produit.setPrix(request.getPrix());
        produit.setQuantity(request.getQuantity());

        // Mise à jour de la catégorie si modifiée
        if (request.getCategorieProduitId() != null) {
            CategorieProduit categorie = categorieProduitRepository.findById(request.getCategorieProduitId())
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            produit.setCategoryProduit(categorie);
        }
//  private List<String> taille;
        // Mise à jour de la taille si modifiée
        if (request.getTaille() != null) {
            String tailleStr = request.getTaille();
           
            produit.setTaille(List.of(tailleStr));  
        }

        // Mise à jour de l'image si une nouvelle est fournie
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            // Suppression de l'ancienne image si elle existe
            if (produit.getImageUrl() != null) {
                String publicId = cloudinaryService.extractPublicIdFromUrl(produit.getImageUrl());
                if (publicId != null) {
                    cloudinaryService.deleteImage(publicId);
                }
            }

            // Upload de la nouvelle image
            String newImageUrl = cloudinaryService.uploadImage(request.getImage());
            produit.setImageUrl(newImageUrl);
        }

        Produit updatedProduit = produitRepository.save(produit);
        return produitMapper.toProduitOneResponse(updatedProduit);
    }

    /**
     * Supprime un produit (optionnel)
     */
    @Transactional
    public void deleteProduit(Long produitId) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // Suppression de l'image Cloudinary
        if (produit.getImageUrl() != null) {
            String publicId = cloudinaryService.extractPublicIdFromUrl(produit.getImageUrl());
            if (publicId != null) {
                cloudinaryService.deleteImage(publicId);
            }
        }

        produitRepository.delete(produit);
    }

    @Transactional(readOnly = true)
    public List<ProduitResponse> rechercherProduits(ProduitFilterRequest filter) {

        // Commence avec une spécification neutre
        Specification<Produit> spec = Specification.where(null);

        // Appliquer les filtres s'ils sont présents dans le DTO
        if (filter.getNomProduit() != null) {
            // Applique le filtre de recherche partielle (LIKE)
            spec = spec.and(ProduitSpecification.hasNomProduitLike(filter.getNomProduit()));
        }

        if (filter.getCategorieId() != null) {
            // Applique le filtre de jointure (IN CATEGORY)
            spec = spec.and(ProduitSpecification.inCategorie(filter.getCategorieId()));
        }

        if (filter.getPrixMin() != null) {
            // Applique le filtre de prix (>=)
            spec = spec.and(ProduitSpecification.prixGreaterThanOrEqualTo(filter.getPrixMin()));
        }

        // Exécuter la requête combinée
        return produitRepository.findAll(spec)
                .stream()
                .map(produitMapper::toProduitResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProduitResponse> listerProduitsPagines(Pageable pageable) {
        // La méthode findAll(Pageable) est héritée de PagingAndSortingRepository (via
        // JpaRepository)
        return produitRepository.findAll(pageable)
                .map(produitMapper::toProduitResponse); // Utiliser .map pour convertir Page<Produit> en
                                                        // Page<ProduitResponse>
    }

    // --- NOUVELLE MÉTHODE 2 : Filtrer et Lister avec pagination ---
    @Override
    @Transactional(readOnly = true)
    public Page<ProduitResponse> rechercherProduitsPagines(ProduitFilterRequest filter, Pageable pageable) {

        // 1. Construire la Spécification (la même logique que dans rechercherProduits
        // non-paginé)
        Specification<Produit> spec = Specification.where(null);

        if (filter.getNomProduit() != null) {
            spec = spec.and(ProduitSpecification.hasNomProduitLike(filter.getNomProduit()));
        }

        if (filter.getCategorieId() != null) {
            spec = spec.and(ProduitSpecification.inCategorie(filter.getCategorieId()));
        }

        // 2. Exécuter la requête avec la Specification ET la Pageable
        // La méthode findAll(Specification<T>, Pageable) est héritée de
        // JpaSpecificationExecutor
        return produitRepository.findAll(spec, pageable)
                .map(produitMapper::toProduitResponse);
    }


    @Override
    @Transactional
    public ProduitOneResponse updateNewPrix(Long produitId, Double newPrix) {
        
        // 1. Trouver le produit existant
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + produitId));

        // 2. Mettre à jour le champ spécifique
        // Vérifiez si le prix est valide avant de le définir
        if (newPrix != null && newPrix >= 0) {
            produit.setNewPrix(newPrix);
        } else {
            // Optionnel: Définir newPrix à 0 ou à -1 pour indiquer l'absence de prix promo
            // Si le prix est invalide ou si l'utilisateur veut retirer la promotion
             produit.setNewPrix(0.0);
        }

        // 3. Sauvegarder la modification
        Produit updatedProduit = produitRepository.save(produit);

        // 4. Retourner le DTO
        return produitMapper.toProduitOneResponse(updatedProduit);
    }
}