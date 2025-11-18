package heritage.africa.go_gainde_service.service;

import heritage.africa.go_gainde_service.entity.Otp;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.entity.enums.OtpType;

public interface OtpService {

    String generateOtp();
     Otp createOtp(Utilisateur user, OtpType type);
    boolean validateOtp(Utilisateur user, String code, OtpType type);

}
