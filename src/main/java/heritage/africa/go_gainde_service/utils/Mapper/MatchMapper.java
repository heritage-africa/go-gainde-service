package heritage.africa.go_gainde_service.utils.Mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import heritage.africa.go_gainde_service.entity.Competition;
import heritage.africa.go_gainde_service.entity.Match;
import heritage.africa.go_gainde_service.web.dto.request.CompetitionRequest;
import heritage.africa.go_gainde_service.web.dto.request.MatchRequest;
import heritage.africa.go_gainde_service.web.dto.response.CompetitionResponse;
import heritage.africa.go_gainde_service.web.dto.response.MatchResponse;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    /**
     * Convertit MatchRequest en Match
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", source = "date", qualifiedByName = "stringToLocalDateTime")
    @Mapping(target = "stadium", ignore = true)
    @Mapping(target = "opponent", ignore = true)
    @Mapping(target = "competition", ignore = true)
    Match toMatch(MatchRequest request);

    /**
     * Convertit Match en MatchResponse
     */
    @Mapping(target = "date", expression = "java(match.getDateFormatted())")
    @Mapping(target = "stadium", source = "stadium.name")
    @Mapping(target = "opponent", source = "opponent.name")
    @Mapping(target = "competition", source = "competition.name")
    MatchResponse toMatchResponse(Match match);



    @Mapping(target= "name", source = "name")
    Competition toCompetitionEntity(CompetitionRequest request);


    // to competition

    CompetitionResponse toCompetitionResponse(Competition competition);

    /**
     * Convertit String en LocalDateTime
     */
    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(date + "T00:00:00");
    }
}
