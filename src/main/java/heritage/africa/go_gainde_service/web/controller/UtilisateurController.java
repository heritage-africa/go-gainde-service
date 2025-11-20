package heritage.africa.go_gainde_service.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.service.UtilisateurService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

private final UtilisateurService utilisateurService;

    ResponseEntity<?> getAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUsers();
        return new ResponseEntity<List<Utilisateur>>(utilisateurs, HttpStatus.OK);
    }
    
}
