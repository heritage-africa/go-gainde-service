package heritage.africa.go_gainde_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import heritage.africa.go_gainde_service.entity.Produit;

@Repository
public interface ProduitRepository extends  JpaRepository<Produit, Long> ,JpaSpecificationExecutor<Produit>{
    
}
