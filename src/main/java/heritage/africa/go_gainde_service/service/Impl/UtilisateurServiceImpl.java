package heritage.africa.go_gainde_service.service.Impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import heritage.africa.go_gainde_service.entity.Otp;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.entity.enums.OtpType;
import heritage.africa.go_gainde_service.entity.enums.Role;
import heritage.africa.go_gainde_service.repository.UtilisateurRepository;
import heritage.africa.go_gainde_service.service.OtpService;
import heritage.africa.go_gainde_service.service.SmsService;
import heritage.africa.go_gainde_service.service.UtilisateurService;
import heritage.africa.go_gainde_service.web.dto.Request.UserRegistrationRequest;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final SmsService smsService;

    public UtilisateurServiceImpl(UtilisateurRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService,  SmsService smsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.smsService = smsService;
    }

    public Utilisateur registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("username already in use");
        }

        Utilisateur user = new Utilisateur();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.ROLE_USER);
        user.setVerified(false);

        return userRepository.save(user);
    }



    public void initiatePhoneVerification(Long userId) {
        Utilisateur user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPhoneNumber() == null) {
            throw new RuntimeException("Phone number not set");
        }

        Otp otp = otpService.createOtp(user, OtpType.PHONE_VERIFICATION);
        smsService.sendOtpSms(user.getPhoneNumber(), otp.getCode());
    }



    public boolean verifyPhoneOtp(Long userId, String code) {
        Utilisateur user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return otpService.validateOtp(user, code, OtpType.PHONE_VERIFICATION);
    }

    @Override
    public Utilisateur getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
