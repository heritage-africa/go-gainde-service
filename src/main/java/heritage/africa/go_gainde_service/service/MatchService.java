package heritage.africa.go_gainde_service.service;

import java.util.List;

import heritage.africa.go_gainde_service.web.dto.request.MatchRequest;
import heritage.africa.go_gainde_service.web.dto.response.MatchResponse;

public interface MatchService {



     public MatchResponse creerMatch(MatchRequest request);

    /**
     * Liste tous les matchs
     */
    public List<MatchResponse> listerMatchs() ;

    /**
     * Récupère le détail d'un match
     */
    public MatchResponse getDetailMatch(Long matchId) ;

    /**
     * Supprime un match
     */
    public void deleteMatch(Long matchId) ;
    
}
