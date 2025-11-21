package heritage.africa.go_gainde_service.web.dto.request;

import lombok.Data;

@Data
public class MatchRequest {
    
    private String date;
    private String time;
    private StadiumRequest stadium;
    private OpponentRequest opponent;
    private boolean isHome;
    // private List<InjuredPlayerDto> injuredPlayers;

    // Getters & Setters
}
