package heritage.africa.go_gainde_service.web.dto.response;

import lombok.Data;

@Data
public class MatchResponse {
    
    private  String competition;
    private String date;
    private String time;
    private String stadium;
    private String opponent;
    private boolean isHome;
    
    // private List<InjuredPlayerDto> injuredPlayers;
}
