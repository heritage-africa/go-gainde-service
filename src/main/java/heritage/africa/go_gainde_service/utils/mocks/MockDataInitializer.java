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









// public allPlayers: PlayerDetail[] = [
//     {
//       // Propriétés héritées de Player
//       id: 1,
//       fullName: 'Nicolas Jackson',
//       club: 'FC Bayern München',
//       positions: ['BU'], // Correspond à Attaquant
//       positionCategory: 'DEFENSEUR',
//       heightCm: 186,
//       birthDate: '20 June 2001',
//       age: 24,
//       marketValueMillions: 49,
//       startDate: 2023, // Supposition pour l'entrée en club/nation
//       photoUrl: 'https://fcbayern-fr.com/wp-content/uploads/2025/09/2025-09-02-qui-est-J.jpg',

//       // Propriétés de PlayerDetail (Détails)
//       clubLogoUrl:
//         'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg/250px-FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg.png',
//       loanStatus: 'Prêt',
//       loanEndDate: '30 Jun 2026',
//       jerseyNumber: 11,

//       // Statistiques de carrière
//       selections: 20,
//       matchesPlayed: 35,
//       goals: 5,
//       trophiesWon: 1,
//       formRating: 7.8,

//       // Position et Attributs
//       preferredFoot: 'Droitier',
//       primaryPosition: 'BU',
//       strength: 'High pressing',
//       weakness: 'Ball control',

//       // Attributs Radar
//       attributes: { VIT: 70, TIR: 60, PAS: 50, DRI: 49, DEF: 33, PHY: 66 },

//       // Historique des transferts
//       transferHistory: [
//         {
//           date: '30 Jun 2026',
//           fromClub: 'FC Bayern München',
//           toClub: 'Chelsea',
//           transferType: 'Fin de prêt',
//           fee: 'Fin de prêt',
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg/250px-FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg.png',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png',
//         },
//         {
//           date: '30 Jun 2026',
//           fromClub: 'FC Bayern München',
//           toClub: 'Chelsea',
//           transferType: 'Fin de prêt',
//           fee: 'Fin de prêt',
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg/250px-FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg.png',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png',
//         },
//         {
//           date: '30 Jun 2026',
//           fromClub: 'FC Bayern München',
//           toClub: 'Chelsea',
//           transferType: 'Fin de prêt',
//           fee: 'Fin de prêt',
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg/250px-FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg.png',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png',
//         },
//         {
//           date: '1 Sept 2025',
//           fromClub: 'Chelsea',
//           toClub: 'FC Bayern München',
//           transferType: 'Prêt',
//           fee: 'Gratuit', // Généralement un prêt n'a pas de frais de transfert (mais peut avoir des frais de prêt)
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg/250px-FC_Bayern_M%C3%BCnchen_logo_%282024%29.svg.png',
//         },
//         {
//           date: '1 Jul 2023',
//           fromClub: 'Villarreal',
//           toClub: 'Chelsea',
//           transferType: 'Transfert',
//           fee: 37,
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/fr/thumb/5/5d/Logo_Villarreal_CF_2009.svg/961px-Logo_Villarreal_CF_2009.svg.png',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/en/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png',
//         },
//         {
//           date: '1 Jul 2022',
//           fromClub: 'Mirandés',
//           toClub: 'Villarreal',
//           transferType: 'Fin de prêt',
//           fee: 'Fin de prêt',
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/fr/4/4d/CD_Mirand%C3%A9s_%28logo%29.svg',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/fr/thumb/5/5d/Logo_Villarreal_CF_2009.svg/961px-Logo_Villarreal_CF_2009.svg.png',
//         },
//         {
//           date: '5 Oct 2020',
//           fromClub: 'Villarreal',
//           toClub: 'Mirandés',
//           transferType: 'Prêt',
//           fee: 'Gratuit',
//           fromClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/fr/thumb/5/5d/Logo_Villarreal_CF_2009.svg/961px-Logo_Villarreal_CF_2009.svg.png',
//           toClubLogoUrl:
//             'https://upload.wikimedia.org/wikipedia/fr/4/4d/CD_Mirand%C3%A9s_%28logo%29.svg',
//         },
//       ],
//     },
//     {
//       // Données d'origine d'Ismaila Sarr (ID 2)
//       id: 2,
//       fullName: 'Ismaila Sarr',
//       club: 'Crystal Palace',
//       positions: ['MO', 'AD'],
//       heightCm: 185,
//       birthDate: '25/02/1998',
//       age: 27,
//       marketValueMillions: 18.0,
//       startDate: 2016,
//       injury: { type: 'Injury', details: 'Hamstring' },
//       photoUrl: 'path/to/ismaila_sarr_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'ATTAQUANT', // Hypothèse basée sur AM, RW
//       clubLogoUrl: 'path/to/palace_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'AD',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { VIT: 0, TIR: 0, PAS: 0, DRI: 0, DEF: 0, PHY: 0 },
//       transferHistory: [],
//     },
//     {
//       // Données d'origine d'Ibrahim Mbaye (ID 3)
//       id: 3,
//       fullName: 'Ibrahim Mbaye',
//       club: 'Paris SG',
//       positions: ['AD'],
//       heightCm: 175,
//       birthDate: '24/01/2008',
//       age: 17,
//       marketValueMillions: 2.0,
//       startDate: 2024,
//       photoUrl: 'path/to/ibrahim_mbaye_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'ATTAQUANT',
//       clubLogoUrl: 'path/to/psg_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'AD',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { VIT: 0, TIR: 0, PAS: 0, DRI: 0, DEF: 0, PHY: 0 },
//       transferHistory: [],
//     },
//     {
//       // Données d'origine d'Assane Diao (ID 4)
//       id: 4,
//       fullName: 'Assane Diao',
//       club: 'Como',
//       positions: ['AD'],
//       heightCm: 185,
//       birthDate: '07/09/2005',
//       age: 20,
//       marketValueMillions: 15.0,
//       startDate: 2024,
//       photoUrl: 'path/to/assane_diao_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'ATTAQUANT',
//       clubLogoUrl: 'path/to/como_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'AD',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { VIT: 0, TIR: 0, PAS: 0, DRI: 0, DEF: 0, PHY: 0 },
//       transferHistory: [],
//     },
//     {
//       // Données d'origine de Boulaye Dia (ID 5)
//       id: 5,
//       fullName: 'Boulaye Dia',
//       club: 'Lazio',
//       positions: ['BU', 'MO'],
//       heightCm: 180,
//       birthDate: '16/11/1996',
//       age: 27,
//       marketValueMillions: 13.0,
//       startDate: 2020,
//       photoUrl: 'path/to/boulaye_dia_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'ATTAQUANT',
//       clubLogoUrl: 'path/to/lazio_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'BU',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { VIT: 0, TIR: 0, PAS: 0, DRI: 0, DEF: 0, PHY: 0 },
//       transferHistory: [],
//     },
//     {
//       // Données d'origine de Kalidou Koulibaly (ID 6)
//       id: 6,
//       fullName: 'Kalidou Koulibaly',
//       club: 'Al-Hilal',
//       positions: ['DC'],
//       heightCm: 187,
//       birthDate: '20/06/1991',
//       age: 34,
//       marketValueMillions: 10.0,
//       startDate: 2015,
//       photoUrl: 'path/to/kalidou_koulibaly_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'DEFENSEUR', // Hypothèse basée sur CB
//       clubLogoUrl: 'path/to/alhilal_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'DC',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { VIT: 0, TIR: 0, PAS: 0, DRI: 0, DEF: 0, PHY: 0 },
//       transferHistory: [],
//     },
//     {
//       // Données d'origine d'Edouard Mendy (ID 7)
//       id: 7,
//       fullName: 'Edouard Mendy',
//       club: 'Al-Ahli',
//       positions: ['G'],
//       heightCm: 197,
//       birthDate: '20/03/1992',
//       age: 33,
//       marketValueMillions: 11.0,
//       startDate: 2019,
//       photoUrl: 'path/to/edouard_mendy_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'GOAL', // Hypothèse basée sur GK
//       clubLogoUrl: 'path/to/alahli_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'G',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { PL: 88, REL: 85, DEG: 80, REF: 90, VIT: 55, POS: 87 },
//       transferHistory: [],
//     },
//     {
//       // Données d'origine de Pape Gueye (ID 8)
//       id: 8,
//       fullName: 'Pape Gueye',
//       club: 'Olympique Marseille',
//       positions: ['MC', 'MDF'],
//       heightCm: 189,
//       birthDate: '24/01/1999',
//       age: 26,
//       marketValueMillions: 8.0,
//       startDate: 2021,
//       photoUrl: 'path/to/pape_gueye_photo.jpg',

//       // Nouvelles propriétés (PlayerDetail)
//       positionCategory: 'MILIEU', // Hypothèse basée sur CM
//       clubLogoUrl: 'path/to/om_logo.png',
//       loanStatus: 'Permanent',
//       loanEndDate: '',
//       jerseyNumber: 0,
//       selections: 0,
//       matchesPlayed: 0,
//       goals: 0,
//       trophiesWon: 0,
//       formRating: 0.0,
//       preferredFoot: 'Droitier',
//       primaryPosition: 'MC',
//       strength: 'Not set',
//       weakness: 'Not set',
//       attributes: { VIT: 0, TIR: 0, PAS: 0, DRI: 0, DEF: 0, PHY: 0 },
//       transferHistory: [],
//     },
//   ];




