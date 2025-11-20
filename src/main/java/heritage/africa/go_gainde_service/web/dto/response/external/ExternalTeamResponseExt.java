package heritage.africa.go_gainde_service.web.dto.response.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExternalTeamResponseExt {

    @JsonProperty("Selectionneurs")
    private SelectionneurResponseExt selectionneurs;

    @JsonProperty("Gardiens de but")
    private List<JoueurResponseExt> gardiens;

    @JsonProperty("Défenseurs")
    private List<JoueurResponseExt> defenseurs;

    @JsonProperty("Milieux de terrain")
    private List<JoueurResponseExt> milieux;

    @JsonProperty("Attaquants")
    private List<JoueurResponseExt> attaquants;
}

