package heritage.africa.go_gainde_service.mobile.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.service.MatchService;
import heritage.africa.go_gainde_service.web.dto.request.MatchRequest;
import heritage.africa.go_gainde_service.web.dto.response.MatchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mobile/matchs")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    /**
     * POST /api/matchs - Créer un nouveau match
     */
     @PostMapping
    public ResponseEntity<MatchResponse> creerMatch(@Valid @RequestBody MatchRequest request) {
        // Suppression du bloc try-catch pour laisser l'exception remonter.
        // Cela forcera le GlobalExceptionHandler à fonctionner ou affichera l'erreur dans la console du serveur.
        MatchResponse match = matchService.creerMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(match);
    }


    /**
     * GET /api/matchs - Lister tous les matchs
     */
    @GetMapping
    public ResponseEntity<List<MatchResponse>> listerMatchs() {
        List<MatchResponse> matchs = matchService.listerMatchs();
        return ResponseEntity.ok(matchs);
    }

    /**
     * GET /api/matchs/{id} - Obtenir le détail d'un match
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> getDetailMatch(@PathVariable Long id) {
        try {
            MatchResponse match = matchService.getDetailMatch(id);
            return ResponseEntity.ok(match);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/matchs/{id} - Supprimer un match
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        try {
            matchService.deleteMatch(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}