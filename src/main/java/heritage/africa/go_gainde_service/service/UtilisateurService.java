package heritage.africa.go_gainde_service.service;

import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.web.dto.Request.UserRegistrationRequest;

public interface UtilisateurService {

    Utilisateur registerUser(UserRegistrationRequest request);
    void initiatePhoneVerification(Long userId);
    boolean verifyPhoneOtp(Long userId, String code);

    Utilisateur getUserByUsername(String username);


}
