package heritage.africa.go_gainde_service.web.dto.response; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionResponse {
    
    private Long id; 
    private String name;
    private String fullName;
    private String logo;
}