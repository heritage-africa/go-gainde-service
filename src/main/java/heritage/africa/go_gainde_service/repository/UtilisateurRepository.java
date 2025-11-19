package heritage.africa.go_gainde_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heritage.africa.go_gainde_service.entity.Utilisateur;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByUsername(String username);

    Optional<Utilisateur> findByUsername(String username);


    Boolean existsByEmail(String email);
    Optional<Utilisateur> findByEmail(String email);
}
