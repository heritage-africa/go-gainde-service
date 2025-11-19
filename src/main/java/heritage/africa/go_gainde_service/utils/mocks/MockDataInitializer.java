package heritage.africa.go_gainde_service.utils.mocks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.entity.enums.Role;
import heritage.africa.go_gainde_service.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MockDataInitializer implements CommandLineRunner {

    private final UtilisateurRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("🚀 MockData démarrage...");

        if (userRepository.findByEmail("admin@accel.tech").isEmpty()) {

            Utilisateur admin = new Utilisateur();
            admin.setEmail("admin@accel.tech");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setVerified(true);
            admin.setRole(Role.ROLE_ADMIN);
            admin.setPhoneNumber("770000000");

            userRepository.save(admin);

            System.out.println("✅ Admin ajouté : admin@accel.tech / 123456");
        } else {
            System.out.println("ℹ️ Admin déjà existant, aucune insertion.");
        }
    }
}
