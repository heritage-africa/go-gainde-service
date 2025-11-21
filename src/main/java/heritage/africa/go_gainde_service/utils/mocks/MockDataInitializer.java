package heritage.africa.go_gainde_service.utils.mocks;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import heritage.africa.go_gainde_service.entity.CategorieProduit;
import heritage.africa.go_gainde_service.entity.Produit;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.entity.enums.Role;
import heritage.africa.go_gainde_service.repository.CategorieProduitRepository;
import heritage.africa.go_gainde_service.repository.ProduitRepository;
import heritage.africa.go_gainde_service.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MockDataInitializer implements CommandLineRunner {

    // Repositories pour les mocks
    private final UtilisateurRepository userRepository;
    private final CategorieProduitRepository categorieProduitRepository;
    private final ProduitRepository produitRepository;
    
    // Outil de sécurité
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("🚀 Initialisation des données de Mock...");
        
        // --- 1. Initialisation des Utilisateurs (Admin) ---
        initializeUsers();

        // --- 2. Initialisation des Catégories et Produits ---
        initializeProductsAndCategories();
        
        System.out.println("✅ Initialisation des données de Mock terminée.");
    }

    // --------------------------------------------------------------------------
    // Méthodes pour l'initialisation des Utilisateurs
    // --------------------------------------------------------------------------

    private void initializeUsers() {
        if (userRepository.findByEmail("admin@accel.tech").isEmpty()) {

            Utilisateur admin = new Utilisateur();
            admin.setEmail("admin@accel.tech");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setVerified(true);
            admin.setRole(Role.ROLE_ADMIN);
            admin.setPhoneNumber("770000000");

            userRepository.save(admin);

            System.out.println("  ➡️ Admin ajouté : admin@accel.tech / 123456");
        } else {
            System.out.println("  ℹ️ Admin déjà existant.");
        }
    }

    // --------------------------------------------------------------------------
    // Méthodes pour l'initialisation des Produits
    // --------------------------------------------------------------------------
    
    private void initializeProductsAndCategories() {
    if (categorieProduitRepository.count() == 0) {
        // --- Création des Catégories ---
        CategorieProduit electronique = createCategory("Électronique");
        CategorieProduit vetement = createCategory("Vêtements");
        CategorieProduit alimentation = createCategory("Alimentation");

        categorieProduitRepository.saveAll(Arrays.asList(electronique, vetement, alimentation));
        System.out.println("  ➡️ Catégories de produits ajoutées.");

        // --- Création des Produits ---
        List<String> telephoneSizes = Arrays.asList("Small", "Medium", "Large");
        Produit telephone = createProduit(
            "Téléphone GoPhone X",
            "Le dernier smartphone avec une caméra IA.",
            999.99,
            50,
            electronique,
            "url_image_gophone.jpg",
            899.99,
            telephoneSizes
        );
        
        List<String> tShirtSizes = Arrays.asList("S", "M", "L", "XL");
        Produit tShirt = createProduit(
            "T-shirt Africain Coton Bio",
            "T-shirt 100% coton bio avec motif wax.",
            25.00,
            200,
            vetement,
            "url_image_tshirt_wax.jpg",
            20.00,
            tShirtSizes
        );
        
        List<String> rizSizes = Arrays.asList("5KG", "10KG");
        Produit riz = createProduit(
            "Riz Long Grain 5KG",
            "Riz de qualité supérieure, idéal pour le thieboudienne.",
            15.50,
            500,
            alimentation,
            "url_image_riz.jpg",
            0.0,
            rizSizes
        );

        produitRepository.saveAll(Arrays.asList(telephone, tShirt, riz));
        System.out.println("  ➡️ Produits de mock ajoutés.");

    } else {
        System.out.println("  ℹ️ Catégories et produits déjà existants.");
    }
}
    
    // Méthodes utilitaires (inchangées)
    private CategorieProduit createCategory(String nom) {
        CategorieProduit cat = new CategorieProduit();
        cat.setNomCategorie(nom);
        return cat;
    }

    private Produit createProduit(String libelle, String description, double prix, int quantity, CategorieProduit category, String imageUrl,double newPrix, List<String> taille) {
    Produit prod = new Produit();
    prod.setLibelle(libelle);
    prod.setDescription(description);
    prod.setPrix(prix);
    prod.setQuantity(quantity);
    prod.setCategoryProduit(category);
    prod.setImageUrl(imageUrl);
    prod.setTaille(taille); // Add this line to set the taille property
    prod.setNewPrix(newPrix);
    
    return prod;
}
}