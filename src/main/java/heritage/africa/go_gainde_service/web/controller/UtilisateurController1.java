package heritage.africa.go_gainde_service.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.config.JwtTokenUtil;
import heritage.africa.go_gainde_service.core.ApiErrorResponse;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.mobile.dto.Request.OtpVerificationRequest;
import heritage.africa.go_gainde_service.security.CustomUserDetails;
import heritage.africa.go_gainde_service.service.SmsService;
import heritage.africa.go_gainde_service.service.UtilisateurService;
import heritage.africa.go_gainde_service.web.dto.request.AuthRequest1;
import heritage.africa.go_gainde_service.web.dto.response.AuthResponse1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/web/auth")
@RequiredArgsConstructor
public class UtilisateurController1 {

    private final UtilisateurService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final SmsService smsService;



@Operation(summary = "Complete registration")
@ApiResponse(responseCode = "200", description = "user logged in successfully")
@ApiResponse(responseCode = "404", description = "user not found")
@PostMapping("/login")
public ResponseEntity<?> authenticateWeb(@RequestBody AuthRequest1 loginRequest) {
    try {
        System.out.println("🔐 Tentative de connexion pour: " + loginRequest.getEmail());
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Utilisateur user = userDetails.getUser();
        
        String token = jwtTokenUtil.generateTokenWithIdentifier(
            userDetails,
            user.getEmail()
        );
        
        System.out.println("✅ Connexion réussie pour: " + user.getEmail());
        
        return ResponseEntity.ok(
            new AuthResponse1(token, user.getId(), user.getEmail(), user.isVerified())
        );
    } catch (BadCredentialsException e) {
        System.out.println("❌ Mauvais mot de passe pour: " + loginRequest.getEmail());
        e.printStackTrace(); // IMPORTANT: voir la stack trace
        return new ResponseEntity<>(
            ApiErrorResponse.validationError("Identifiant ou mot de passe incorrect"), 
            HttpStatus.BAD_REQUEST
        );
    } catch (UsernameNotFoundException e) {
        System.out.println("❌ Utilisateur non trouvé: " + loginRequest.getEmail());
        e.printStackTrace(); // IMPORTANT: voir la stack trace
        return new ResponseEntity<>(
            ApiErrorResponse.validationError("Identifiant ou mot de passe incorrect"), 
            HttpStatus.BAD_REQUEST
        );
    } catch (Exception e) {
        System.out.println("❌ Erreur inattendue: " + e.getClass().getName());
        e.printStackTrace(); // IMPORTANT: voir la stack trace complète
        return new ResponseEntity<>(
            ApiErrorResponse.validationError("Identifiant ou mot de passe incorrect"), 
            HttpStatus.BAD_REQUEST
        );
    }
}



    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody OtpVerificationRequest request) {
        boolean isValid = userService.verifyEmailOtp(request.getUserId(), request.getCode());
        
        if (isValid) {
            return ResponseEntity.ok("Email verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
    
    // @PostMapping("/resend-email-otp")
    // public ResponseEntity<?> resendEmailOtp(@RequestParam String email) {
    //     userService.initiateEmailVerification(email);
    //     return ResponseEntity.ok("OTP resent successfully");
    // }
    

    @Operation(summary = "verify phone")
    @ApiResponse(responseCode = "200", description = "phone verified successfully")
    @ApiResponse(responseCode = "404", description = "phone not found")
    @PostMapping("/verify-phone")
    public ResponseEntity<?> verifyPhone(@RequestBody OtpVerificationRequest request) {
        boolean isValid = userService.verifyPhoneOtp(request.getUserId(), request.getCode());
        
        if (isValid) {
            return ResponseEntity.ok("Phone verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
    
}
