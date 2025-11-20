package heritage.africa.go_gainde_service.service.external;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import heritage.africa.go_gainde_service.web.dto.response.external.ExternalTeamResponseExt;

@Service
public class ExternalApiMockService {

    public ExternalTeamResponseExt getTeam() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(
                new ClassPathResource("mocks/TeamMock.json").getFile(),
                ExternalTeamResponseExt.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du mock", e);
        }
    }
}
