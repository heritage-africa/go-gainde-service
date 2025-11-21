package heritage.africa.go_gainde_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import heritage.africa.go_gainde_service.mobile.dto.Request.ProduitRequest;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitOneResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.ProduitResponse;
import heritage.africa.go_gainde_service.web.dto.request.ProduitFilterRequest;




public interface ProduitService {

    List<ProduitResponse> listerProduits ();

    ProduitOneResponse getDetailProduit(Long id);


    ProduitOneResponse creerProduit(ProduitRequest produitResponse);



    ProduitOneResponse updateProduit(Long id, ProduitRequest produitResponse);


    void deleteProduit(Long id);


    List<ProduitResponse> rechercherProduits(ProduitFilterRequest filter);



    Page<ProduitResponse> listerProduitsPagines(Pageable pageable);



    Page<ProduitResponse> rechercherProduitsPagines(ProduitFilterRequest filter, Pageable pageable);



    ProduitOneResponse updateNewPrix(Long produitId, Double newPrix);
    
}
