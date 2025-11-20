package heritage.africa.go_gainde_service.utils.Mapper;

import org.mapstruct.Mapper;

import heritage.africa.go_gainde_service.web.dto.response.PlayerResponse;
import heritage.africa.go_gainde_service.web.dto.response.SelectionneurResponse;
import heritage.africa.go_gainde_service.web.dto.response.TeamResponse;
import heritage.africa.go_gainde_service.web.dto.response.external.ExternalTeamResponseExt;
import heritage.africa.go_gainde_service.web.dto.response.external.JoueurResponseExt;
import heritage.africa.go_gainde_service.web.dto.response.external.SelectionneurResponseExt;

@Mapper(componentModel = "spring")
public interface TeamMapper {


    PlayerResponse toPlayerResponse (JoueurResponseExt joueurResponseExt);


    SelectionneurResponse toSelectionneurResponse (SelectionneurResponseExt selectionneurResponseExt);


    



    TeamResponse toTeamResponse (ExternalTeamResponseExt externalTeamResponseExt);




        
    
}
