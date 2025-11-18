package heritage.africa.go_gainde_service.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.config.JwtTokenUtil;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.service.UtilisateurService;
import heritage.africa.go_gainde_service.web.dto.Request.AuthRequest;
import heritage.africa.go_gainde_service.web.dto.Request.UserRegistrationRequest;
import heritage.africa.go_gainde_service.web.dto.Response.AuthResponse;
import lombok.RequiredArgsConstructor;

// AuthController.java
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UtilisateurController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UtilisateurService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        Utilisateur user = userService.registerUser(request);
        
        // Initiate email verification
        userService.initiatePhoneVerification(user.getId());
        
        return ResponseEntity.ok("User registered successfully. Verification OTP sent to email.");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        Utilisateur user = userService.getUserByUsername(request.getUsername());
        
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername(), user.isVerified()));
    }
    
    // @PostMapping("/verify-email")
    // public ResponseEntity<?> verifyEmail(@RequestBody OtpVerificationRequest request) {
    //     boolean isValid = userService.verifyEmailOtp(request.getUserId(), request.getCode());
        
    //     if (isValid) {
    //         return ResponseEntity.ok("Email verified successfully");
    //     } else {
    //         return ResponseEntity.badRequest().body("Invalid or expired OTP");
    //     }
    // }
    
    // @PostMapping("/resend-email-otp")
    // public ResponseEntity<?> resendEmailOtp(@RequestParam String email) {
    //     userService.initiateEmailVerification(email);
    //     return ResponseEntity.ok("OTP resent successfully");
    // }
    
    // @PostMapping("/verify-phone")
    // public ResponseEntity<?> verifyPhone(@RequestBody OtpVerificationRequest request) {
    //     boolean isValid = userService.verifyPhoneOtp(request.getUserId(), request.getCode());
        
    //     if (isValid) {
    //         return ResponseEntity.ok("Phone verified successfully");
    //     } else {
    //         return ResponseEntity.badRequest().body("Invalid or expired OTP");
    //     }
    // }
    
    @PostMapping("/initiate-phone-verification")
    public ResponseEntity<?> initiatePhoneVerification(@RequestParam Long userId) {
        userService.initiatePhoneVerification(userId);
        return ResponseEntity.ok("OTP sent to phone number");
    }
}