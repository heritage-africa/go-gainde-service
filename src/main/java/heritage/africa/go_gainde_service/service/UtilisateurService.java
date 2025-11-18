package heritage.africa.go_gainde_service.service;

import heritage.africa.go_gainde_service.dto.UserRegistrationRequest;
import heritage.africa.go_gainde_service.entity.Utilisateur;

public interface UtilisateurService {

    Utilisateur registerUser(UserRegistrationRequest request);
    void initiatePhoneVerification(Long userId);
    boolean verifyPhoneOtp(Long userId, String code);

}
