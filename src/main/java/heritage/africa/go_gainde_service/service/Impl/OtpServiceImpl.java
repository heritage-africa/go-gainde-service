package heritage.africa.go_gainde_service.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import heritage.africa.go_gainde_service.entity.Otp;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.entity.enums.OtpType;
import heritage.africa.go_gainde_service.repository.OtpRepository;
import heritage.africa.go_gainde_service.service.OtpService;
import heritage.africa.go_gainde_service.utils.exception.NotFoundException;

@Service
public class OtpServiceImpl implements OtpService {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRATION_MINUTES = 5;

    private final OtpRepository otpRepository;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateOtp() {
        // Generate a 6-digit numeric OTP
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public Otp createOtp(Utilisateur user, OtpType type) {
        // Clean up expired OTPs for this user
        cleanUpExpiredOtps(user.getId().toString());

        // Generate and save new OTP
        String code = generateOtp();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(OTP_EXPIRATION_MINUTES);

        Otp otp = new Otp();
        otp.setCode(code);
        otp.setCreatedAt(now);
        otp.setExpiresAt(expiresAt);
        otp.setVerified(false);
        otp.setType(type);
        
        // otp.setUser(user);

        return otpRepository.save(otp);
    }

    public boolean validateOtp(Utilisateur user, String code, OtpType type) {
        LocalDateTime now = LocalDateTime.now();


        // PAS PHONE number ici
        Optional<Otp> otpOptional = otpRepository
        .findByCodeAndPhoneNumberAndTypeAndVerifiedIsFalseAndExpiresAtAfter(
            code, user.getPhoneNumber(), type, now
        );

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            otp.setVerified(true);
            otpRepository.save(otp);
            return true;
        }

         throw new NotFoundException("OTP not found");
    }

    private void cleanUpExpiredOtps(String userId) {
        LocalDateTime now = LocalDateTime.now();
        List<Otp> expiredOtps = otpRepository.findByPhoneNumberAndVerifiedIsFalseAndExpiresAtBefore(userId, now);
        otpRepository.deleteAll(expiredOtps);
    }
}
