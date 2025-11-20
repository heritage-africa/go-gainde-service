package heritage.africa.go_gainde_service.web.dto.response.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StatsSelectionneurResponseExt {
    @JsonProperty("sélections")
    private Integer selections;

    private Integer buts;
}

