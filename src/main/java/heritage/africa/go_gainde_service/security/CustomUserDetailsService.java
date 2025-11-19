// package heritage.africa.go_gainde_service.security;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import heritage.africa.go_gainde_service.entity.Utilisateur;
// import heritage.africa.go_gainde_service.repository.UtilisateurRepository;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class CustomUserDetailsService implements UserDetailsService {


    
//     private final UtilisateurRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         System.out.println("🔍 Recherche utilisateur: " + username);
        
//         // Replace with your actual user entity and repository
//         Utilisateur user = userRepository.findByUsername(username)
//             .orElseThrow(() -> {
//                 System.out.println("❌ Utilisateur non trouvé dans la DB: " + username);
//                 return new UsernameNotFoundException("User not found: " + username);
//             });
        
//         System.out.println("✅ Utilisateur trouvé dans DB: " + user.getUsername());
//         System.out.println("🔒 Password hash dans DB: " + user.getPassword());
//         System.out.println("🏷️ Role: " + user.getRole());
        
//         return new CustomUserDetails(user);
//     }
    
// }




package heritage.africa.go_gainde_service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    System.out.println("🔍 Recherche utilisateur avec identifiant: " + identifier);

    Utilisateur user = 
        userRepository.findByPhoneNumber(identifier)
        .or(() -> userRepository.findByEmail(identifier))
        .or(() -> userRepository.findByUsername(identifier))
        .orElseThrow(() -> {
            System.out.println("❌ Aucun utilisateur trouvé avec: " + identifier);
            return new UsernameNotFoundException("Invalid login identifier: " + identifier);
        });

    System.out.println("✅ Trouvé: " + user.getUsername());
    return new CustomUserDetails(user);
    }

    // public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    //     System.out.println("🔍 Recherche utilisateur avec identifiant: " + identifier);
        
    //     // Chercher par USERNAME d'abord, puis par EMAIL si non trouvé
    //     Utilisateur user = userRepository.findByPhoneNumber(identifier)
    //         .or(() -> {
    //             System.out.println("⚠️ Pas trouvé par username, recherche par email...");
    //             return userRepository.findByEmail(identifier);
    //         })
    //         .orElseThrow(() -> {
    //             System.out.println("❌ Utilisateur non trouvé (ni username ni email): " + identifier);
    //             return new UsernameNotFoundException("User not found with identifier: " + identifier);
    //         });
        
    //     System.out.println("✅ Utilisateur trouvé dans DB:");
    //     System.out.println("   - Username: " + user.getUsername());
    //     System.out.println("   - Email: " + user.getEmail());
    //     System.out.println("   - Role: " + user.getRole());
    //     System.out.println("🔒 Password hash: " + user.getPassword());
        
    //     return new CustomUserDetails(user);
    // }
}