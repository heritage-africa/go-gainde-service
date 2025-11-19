package heritage.africa.go_gainde_service.mobile.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.config.JwtTokenUtil;
import heritage.africa.go_gainde_service.core.ApiErrorResponse;
import heritage.africa.go_gainde_service.entity.Otp;
import heritage.africa.go_gainde_service.entity.Utilisateur;
import heritage.africa.go_gainde_service.entity.enums.OtpType;
import heritage.africa.go_gainde_service.entity.enums.Role;
import heritage.africa.go_gainde_service.mobile.dto.Request.AuthRequest;
import heritage.africa.go_gainde_service.mobile.dto.Request.CompleteRegistrationRequest;
import heritage.africa.go_gainde_service.mobile.dto.Request.SendOtpRequest;
import heritage.africa.go_gainde_service.mobile.dto.Request.UserRegistrationRequest;
import heritage.africa.go_gainde_service.mobile.dto.Request.VerifyOtpRequest;
import heritage.africa.go_gainde_service.mobile.dto.Response.AuthResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.SendOtpResponse;
import heritage.africa.go_gainde_service.mobile.dto.Response.VerifyOtpResponse;
import heritage.africa.go_gainde_service.repository.OtpRepository;
import heritage.africa.go_gainde_service.repository.UtilisateurRepository;
import heritage.africa.go_gainde_service.security.CustomUserDetails;
import heritage.africa.go_gainde_service.service.OtpService;
import heritage.africa.go_gainde_service.service.SmsService;
import heritage.africa.go_gainde_service.service.UtilisateurService;
import heritage.africa.go_gainde_service.utils.exception.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import java.util.Map;


// AuthController.java
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UtilisateurController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UtilisateurService userService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository userRepository;
    private final SmsService smsService;
    private final OtpRepository otpRepository;
    

    @ApiResponse(responseCode = "200", description = "user logged in successfully")
    @ApiResponse(responseCode = "404", description = "user not found")
    // @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        // try{
        //     Utilisateur user = userService.registerUser(request);
        
        //     userService.initiatePhoneVerification(user.getId());
        
        //     return ResponseEntity.ok("User registered successfully. Verification OTP sent.");
        // }
        // catch (ValidationErrorException e) {
        //     return new ResponseEntity<>(
        //         ApiErrorResponse.validationError(e.getMessage()), 
        //         HttpStatus.BAD_REQUEST
        //     );
        // }
        // catch (NotFoundException e) {
        //     return new ResponseEntity<>(
        //         ApiErrorResponse.notFound(e.getMessage()),
        //         HttpStatus.BAD_REQUEST
        //     );
        // }
        // catch (Exception e) {
        //     return new ResponseEntity<>(
        //         ApiErrorResponse.internalServerError(e.getMessage()), 
        //         HttpStatus.INTERNAL_SERVER_ERROR
        //     );
        // }

        Utilisateur user = userService.registerUser(request);
        
            userService.initiatePhoneVerification(user.getId());
        
            return ResponseEntity.ok("User registered successfully. Verification OTP sent.");
        
    }
    

    // @Operation(summary = "Login user")
    // @ApiResponse(responseCode = "200", description = "user logged in successfully")
    // @ApiResponse(responseCode = "404", description = "user not found")
    // @PostMapping("/login")
    // public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest request) throws Exception {
    //     try {
    //         authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    //         );
    //     } catch (BadCredentialsException e) {
    //         throw new Exception("Incorrect username or password", e);
    //     }
    //     catch (ValidationErrorException e) {
    //         return new ResponseEntity<>(
    //             ApiErrorResponse.insufficientBalance("incorrect username or password"), 
    //             HttpStatus.BAD_REQUEST
    //         );
            
    //     }
    //     catch (NotFoundException e) {
    //         return new ResponseEntity<>(
    //             ApiErrorResponse.notFound(e.getMessage()),
    //             HttpStatus.BAD_REQUEST
    //         );
    //     }
    //     catch (Exception e) {
    //         return new ResponseEntity<>(
    //             ApiErrorResponse.internalServerError(e.getMessage()), 
    //             HttpStatus.INTERNAL_SERVER_ERROR
    //         );
    //     }
        
    //     final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    //     final String token = jwtTokenUtil.generateToken(userDetails);
        
    //     Utilisateur user = userService.getUserByUsername(request.getUsername());
        
    //     return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername(), user.isVerified()));
    // }


    // AuthController.java

@PostMapping("/login")
public ResponseEntity<?> authenticateWeb(@RequestBody AuthRequest loginRequest) {
    try {
        // 1. Authentification
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getNumeroTelephone(),
                loginRequest.getPassword()
            )
        );
        
        // 2. Récupérer les détails (CustomUserDetails)
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        // 3. Récupérer l'entité Utilisateur depuis CustomUserDetails
        Utilisateur user = userDetails.getUser(); // Vous devez avoir un getter
        
        // 4. Générer le token avec le username comme identifiant
        String token = jwtTokenUtil.generateTokenWithIdentifier(
            userDetails,
            user.getPhoneNumber()
        );
        
        // 5. Retourner la réponse
        return ResponseEntity.ok(
            new AuthResponse(token, user.getId(), user.getPhoneNumber(), user.isVerified())
        );
        
    } catch (Exception e) {
        return new ResponseEntity<>(
            ApiErrorResponse.validationError("Identifiant ou mot de passe incorrect"), 
            HttpStatus.BAD_REQUEST
        );
    }
}


    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@CookieValue(value = "jwt_token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
        }

        try {
            // Décoder le username à partir du token
            String username = jwtTokenUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            CustomUserDetails customUser = (CustomUserDetails) userDetails;

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", customUser.getUser().getId());
            userInfo.put("role", customUser.getUser().getRole().name());

            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide ou expiré");
        }
    }

    // @PostMapping("/verify-otp")
    // public ResponseEntity<?> verifyEmail(@RequestBody OtpVerificationRequest request) {
    //    try {
    //         Utilisateur user = userService.getUserById(request.getUserId());
    //     boolean isValid = otpService.validateOtp(user, request.getCode(), OtpType.PHONE_VERIFICATION);
    //     if (isValid) {
    //         return ResponseEntity.ok("otp verified successfully");
    //     } else {
    //         return ResponseEntity.badRequest().body("Invalid or expired OTP");
    //     }
    //    }
    //    catch (NotFoundException e) {
    //         return new ResponseEntity<>(
    //             ApiErrorResponse.notFound(e.getMessage()),
    //             HttpStatus.BAD_REQUEST
    //         );
    //     }
    //    catch (ValidationErrorException e) {
    //     return new ResponseEntity<>(
    //         ApiErrorResponse.validationError(e.getMessage()), 
    //         HttpStatus.BAD_REQUEST
    //     );
    //    } 
    // }
    
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





    @PostMapping("/send-otp")
public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest request) {
    // 1. Vérifier si le numéro existe déjà
    if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
        return ResponseEntity.badRequest()
            .body("Ce numéro est déjà utilisé");
    }
    
    // 2. Générer l'OTP
    String otpCode = otpService.generateOtp();
    
    // 3. Créer l'entité OTP (PAS le User !)
    Otp otp = new Otp();
    otp.setCode(otpCode);
    otp.setPhoneNumber(request.getPhoneNumber());
    otp.setTempUsername(request.getUsername());  // Stocke temporairement
    otp.setCreatedAt(LocalDateTime.now());
    otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
    otp.setType(OtpType.PHONE_VERIFICATION);
    otp.setLatitude(request.getLatitude());
    otp.setLongitude(request.getLongitude());
    LocalDate dateNaissance = LocalDate.parse(request.getDateNaissance(), DateTimeFormatter.ISO_DATE);
    otp.setDateNaissance(dateNaissance);
    otp.setVerified(false);
    otpRepository.save(otp);
    
    // 4. Envoyer le SMS via Twilio
    smsService.sendOtpSms(request.getPhoneNumber(), 
        "Votre code OTP : " + otpCode);
    
    return ResponseEntity.ok(new SendOtpResponse(otp.getId(), 
        "OTP envoyé avec succès"));
}

@PostMapping("/verify-otp")
public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
    try {
        // 1. Récupérer l'OTP
        Otp otp = otpRepository.findById(request.getOtpId())
            .orElseThrow(() -> new NotFoundException("OTP non trouvé"));
        
        // 2. Vérifications de sécurité
        if (otp.isUsed()) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Ce code a déjà été utilisé"));
        }
        
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Ce code a expiré. Demandez un nouveau code."));
        }
        
        if (!otp.getCode().equals(request.getCode())) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Code incorrect"));
        }
        
        // 3. Marquer comme vérifié (MAIS PAS ENCORE UTILISÉ)
        otp.setVerified(true);
        otpRepository.save(otp);
        
        // 4. Retourner un message de succès
        // Le user peut maintenant créer son mot de passe
        return ResponseEntity.ok(new VerifyOtpResponse(
            otp.getId(),
            "Téléphone vérifié avec succès. Vous pouvez maintenant créer votre mot de passe.",
            otp.getTempUsername(),
            otp.getPhoneNumber()
        ));
        
    } catch (NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiErrorResponse.notFound(e.getMessage()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiErrorResponse.internalServerError(e.getMessage()));
    }
}

@PostMapping("/register")
public ResponseEntity<?> completeRegistration(@RequestBody CompleteRegistrationRequest request) {
    try {
        // 1. Récupérer l'OTP vérifié
        Otp otp = otpRepository.findById(request.getOtpId())
            .orElseThrow(() -> new NotFoundException("OTP non trouvé"));
        
        // 2. Vérifications de sécurité
        if (!otp.isVerified()) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Vous devez d'abord vérifier le code OTP"));
        }
        
        if (otp.isUsed()) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Ce code a déjà été utilisé pour créer un compte"));
        }
        
        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("La session a expiré. Recommencez le processus."));
        }
        
        // 3. Valider le mot de passe
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Le mot de passe doit contenir au moins 6 caractères"));
        }
        
        // 4. Vérifier à nouveau que le username et le téléphone ne sont pas pris
        if (userRepository.existsByUsername(otp.getTempUsername())) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Ce username est déjà utilisé"));
        }
        
        if (userRepository.existsByPhoneNumber(otp.getPhoneNumber())) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError("Ce numéro est déjà utilisé"));
        }
        
        // 5. MAINTENANT on crée le User !
        Utilisateur user = new Utilisateur();
        user.setUsername(otp.getTempUsername());
        user.setPhoneNumber(otp.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setVerified(true);  // ← Déjà vérifié via OTP !
        user.setRole(Role.ROLE_USER);
        
        
        // Ajouter d'autres champs si nécessaire
        userRepository.save(user);
        
        // 6. Marquer l'OTP comme utilisé
        otp.setUsed(true);
        otpRepository.save(otp);
        
        // 7. Générer le token JWT
        UserDetails userDetails = new CustomUserDetails(user);
        String token = jwtTokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponse(
            token, 
            user.getId(), 
            user.getPhoneNumber(), 
            user.isVerified()
        ));
        
    } catch (NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiErrorResponse.notFound(e.getMessage()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiErrorResponse.internalServerError(e.getMessage()));
    }
}
}