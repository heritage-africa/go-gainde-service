package heritage.africa.go_gainde_service.web.dto.response.external;

import java.util.List;

import lombok.Data;

@Data
public class SelectionneurResponseExt {
    private String nom;
    private Integer age;
    private String faits;
    private String naissance;
    private List<String> parcours;
    private StatsSelectionneurResponseExt stats;
}


