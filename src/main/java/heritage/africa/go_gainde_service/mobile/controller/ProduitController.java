package heritage.africa.go_gainde_service.mobile.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.mobile.dto.Request.ProduitRequest;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitOneResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitResponse;
import heritage.africa.go_gainde_service.service.ProduitService;
import heritage.africa.go_gainde_service.web.dto.request.ProduitFilterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mobile/produits")
@RequiredArgsConstructor
public class ProduitController {

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




    


    
}