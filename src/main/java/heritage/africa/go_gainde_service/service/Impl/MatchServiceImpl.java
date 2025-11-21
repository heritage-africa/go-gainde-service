package heritage.africa.go_gainde_service.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import heritage.africa.go_gainde_service.entity.Competition;
import heritage.africa.go_gainde_service.entity.Match;
import heritage.africa.go_gainde_service.entity.Opponent;
import heritage.africa.go_gainde_service.entity.Stade;
import heritage.africa.go_gainde_service.repository.CompetitionRepository;
import heritage.africa.go_gainde_service.repository.MatchRepository;
import heritage.africa.go_gainde_service.repository.OpponentRepository;
import heritage.africa.go_gainde_service.repository.StadeRepository;
import heritage.africa.go_gainde_service.service.MatchService;
import heritage.africa.go_gainde_service.utils.Mapper.MatchMapper;
import heritage.africa.go_gainde_service.web.dto.request.MatchRequest;
import heritage.africa.go_gainde_service.web.dto.response.MatchResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final StadeRepository stadeRepository;
    private final OpponentRepository opponentRepository;
    private final CompetitionRepository competitionRepository;
    private final MatchMapper matchMapper;

    /**
     * Crée un nouveau match avec ses relations
     */
    @Transactional
    public MatchResponse creerMatch(MatchRequest request) {
        // Conversion du DTO en entité via MapStruct
        Match match = matchMapper.toMatch(request);

        // Création ou récupération du stade
        Stade stade = null;
        if (request.getStadium() != null) {
            if (request.getStadium().getId() != null) {
                stade = stadeRepository.findById(request.getStadium().getId())
                        .orElseThrow(() -> new RuntimeException("Stade non trouvé"));
            } else {
                stade = new Stade();
                stade.setName(request.getStadium().getName());
                stade.setCity(request.getStadium().getCity());
                stade.setCountry(request.getStadium().getCountry());
            }
        }

        // Création ou récupération de l'adversaire
        Opponent opponent = null;
        if (request.getOpponent() != null) {
            if (request.getOpponent().getId() != null) {
                opponent = opponentRepository.findById(request.getOpponent().getId())
                        .orElseThrow(() -> new RuntimeException("Adversaire non trouvé"));
            } else {
                opponent = new Opponent();
                opponent.setName(request.getOpponent().getName());
                opponent.setLogo(request.getOpponent().getLogo());
            }
        }

        Competition competition = null;
        if (request.getCompetition() != null) {
            if (request.getCompetition().getId() != null) {
                competition = competitionRepository.findById(request.getCompetition().getId())
                        .orElseThrow(() -> new RuntimeException("Compétition non trouvée"));
            } else {
                competition = new Competition();
                competition.setName(request.getCompetition().getName());
                competition.setFullName(request.getCompetition().getFullName());
                competition.setLogo(request.getCompetition().getLogo());

            }
        }
        

        // Sauvegarde du match d'abord
        Match savedMatch = matchRepository.save(match);

        // Association et sauvegarde du stade
        if (stade != null) {
            stade.setMatch(savedMatch);
            stadeRepository.save(stade);
        }

        // Association et sauvegarde de l'adversaire
        if (opponent != null) {
            opponent.setMatch(savedMatch);
            opponentRepository.save(opponent);
        }
        // Association et sauvegarde de la compétition
        if (competition != null) {
            competition.setMatch(savedMatch);
            competitionRepository.save(competition);
        }

        // Conversion en DTO de réponse via MapStruct
        return matchMapper.toMatchResponse(savedMatch);
    }

    /**
     * Liste tous les matchs
     */
    @Transactional(readOnly = true)
    public List<MatchResponse> listerMatchs() {
        return matchRepository.findAll()
                .stream()
                .map(matchMapper::toMatchResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère le détail d'un match
     */
    @Transactional(readOnly = true)
    public MatchResponse getDetailMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match non trouvé avec l'ID: " + matchId));

        return matchMapper.toMatchResponse(match);
    }

    /**
     * Supprime un match
     */
    @Transactional
    public void deleteMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match non trouvé"));

        // Suppression des relations
        if (match.getStadium() != null) {
            stadeRepository.delete(match.getStadium());
        }
        if (match.getOpponent() != null) {
            opponentRepository.delete(match.getOpponent());
        }

        matchRepository.delete(match);
    }
}