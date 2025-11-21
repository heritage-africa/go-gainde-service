package heritage.africa.go_gainde_service.web.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.mobile.dto.Request.ProduitRequest;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitOneResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitResponse;
import heritage.africa.go_gainde_service.service.ProduitService;
import heritage.africa.go_gainde_service.web.dto.request.ProduitFilterRequest;
import heritage.africa.go_gainde_service.web.dto.request.UpdateNewPrixRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/web/produits")
@RequiredArgsConstructor
public class ProduitController1 {

    private final ProduitService produitService;

    /**
     * POST /api/produits - Créer un nouveau produit avec image
     * Content-Type: multipart/form-data
     */

    @GetMapping("/search")
    public ResponseEntity<List<ProduitResponse>> searchProduits(
            @ModelAttribute ProduitFilterRequest filter) {

        List<ProduitResponse> produits = produitService.rechercherProduits(filter);
        return ResponseEntity.ok(produits);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProduitOneResponse> creerProduit(
            @Valid @ModelAttribute ProduitRequest request) {
        try {
            ProduitOneResponse produit = produitService.creerProduit(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(produit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ProduitResponse>> listerProduitsPagines(
            @PageableDefault(size = 20, sort = "libelle") Pageable pageable) { // Valeurs par défaut optionnelles

        Page<ProduitResponse> produits = produitService.listerProduitsPagines(pageable);
        return ResponseEntity.ok(produits);
    }

    // --- NOUVELLE FONCTION 2 : Filtrer et Lister avec Pagination ---
    /**
     * GET /api/v1/web/produits/search/paged - Filtrer les produits avec pagination
     * Exemple d'appel:
     * /api/v1/web/produits/search/paged?categorieId=5&size=10&page=1
     */
    @GetMapping("/search/paged")
    public ResponseEntity<Page<ProduitResponse>> searchProduitsPagines(
            @ModelAttribute ProduitFilterRequest filter,
            @PageableDefault(size = 20, sort = "libelle") Pageable pageable) {

        Page<ProduitResponse> produits = produitService.rechercherProduitsPagines(filter, pageable);
        return ResponseEntity.ok(produits);
    }

    /**
     * GET /api/produits - Lister tous les produits
     */
    @GetMapping("")
    public ResponseEntity<List<ProduitResponse>> listerProduits() {
        List<ProduitResponse> produits = produitService.listerProduits();
        return ResponseEntity.ok(produits);
    }

    /**
     * GET /api/produits/{id} - Obtenir le détail d'un produit
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitOneResponse> getDetailProduit(@PathVariable Long id) {
        try {
            ProduitOneResponse produit = produitService.getDetailProduit(id);
            return ResponseEntity.ok(produit);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * PUT /api/produits/{id} - Mettre à jour un produit
     * Content-Type: multipart/form-data
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProduitOneResponse> updateProduit(
            @PathVariable Long id,
            @Valid @ModelAttribute ProduitRequest request) {
        try {
            ProduitOneResponse produit = produitService.updateProduit(id, request);
            return ResponseEntity.ok(produit);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/produits/{id} - Supprimer un produit
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        try {
            produitService.deleteProduit(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}/new-price")
    public ResponseEntity<ProduitOneResponse> updateNewPrix(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNewPrixRequest request) {
        try {
            ProduitOneResponse produit = produitService.updateNewPrix(id, request.getNewPrix());
            return ResponseEntity.ok(produit);
        } catch (RuntimeException e) {
            // Gérer NotFoundException (Produit non trouvé)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // --- NOUVELLE FONCTION 1 : Lister avec Pagination ---

}