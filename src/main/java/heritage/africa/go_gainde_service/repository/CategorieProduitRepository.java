package heritage.africa.go_gainde_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heritage.africa.go_gainde_service.entity.CategorieProduit;


@Repository
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, Long> {
    
}
