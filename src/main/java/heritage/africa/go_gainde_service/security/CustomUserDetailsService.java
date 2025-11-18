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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Recherche utilisateur: " + username);
        
        // Replace with your actual user entity and repository
        Utilisateur user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                System.out.println("❌ Utilisateur non trouvé dans la DB: " + username);
                return new UsernameNotFoundException("User not found: " + username);
            });
        
        System.out.println("✅ Utilisateur trouvé dans DB: " + user.getUsername());
        System.out.println("🔒 Password hash dans DB: " + user.getPassword());
        System.out.println("🏷️ Role: " + user.getRole());
        
        return new CustomUserDetails(user);
    }
    
}
