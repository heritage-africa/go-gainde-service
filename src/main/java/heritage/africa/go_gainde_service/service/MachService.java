package heritage.africa.go_gainde_service.service;

import java.util.List;

import heritage.africa.go_gainde_service.web.dto.request.MatchRequest;
import heritage.africa.go_gainde_service.web.dto.response.MatchResponse;

public interface MachService {

MatchResponse createMatch(MatchRequest request);



    public MatchResponse creerMatch(MatchRequest request) ;


    public List<MatchResponse> listerMatchs() ;

    public MatchResponse getDetailMatch(Long matchId) ;

   
    public void deleteMatch(Long matchId) ;
    
}
