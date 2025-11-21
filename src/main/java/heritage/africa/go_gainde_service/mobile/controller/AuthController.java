package heritage.africa.go_gainde_service.mobile.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import heritage.africa.go_gainde_service.config.JwtTokenUtil;
import heritage.africa.go_gainde_service.core.ApiErrorResponse;
import heritage.africa.go_gainde_service.core.ApiSuccessResponse;
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
import heritage.africa.go_gainde_service.web.dto.response.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;


// AuthController.java
@RestController
@RequestMapping("/api/v1/mobile/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UtilisateurService userService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository userRepository;
    private final SmsService smsService;
    private final OtpRepository otpRepository;
      private static final int OTP_LENGTH = 6;

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


    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "200", description = "user logged in successfully")
    @ApiResponse(responseCode = "404", description = "user not found")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateWeb(@RequestBody AuthRequest loginRequest) {
        try {
            // 1. Authentification
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getNumeroTelephone(),
                    loginRequest.getCodeSecret()
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


    // le logout 

    @Operation(summary = "Logout user")
    @ApiResponse(responseCode = "200", description = "user logged in successfully")
    @ApiResponse(responseCode = "404", description = "user not found")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
       return ResponseEntity.ok()
               .header(HttpHeaders.SET_COOKIE, cookie.toString())
               .body(RestResponse.response(HttpStatus.OK, null, "Logout"));

    }



    @Operation(summary = "Get current user")
    @ApiResponse(responseCode = "200", description = "user logged in successfully")
    @ApiResponse(responseCode = "404", description = "user not found")
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


    


    @Operation(summary = "Initiate phone verification")
    @ApiResponse(responseCode = "200", description = "user logged in successfully")
    @ApiResponse(responseCode = "404", description = "user not found")
    @PostMapping("/initiate-phone-verification")
    public ResponseEntity<?> initiatePhoneVerification(@RequestParam Long userId) {
        userService.initiatePhoneVerification(userId);
        return ResponseEntity.ok("OTP sent to phone number");
    }





    @Operation(summary = "Send OTP")
    @ApiResponse(responseCode = "200", description = "otp sent successfully")
    @ApiResponse(responseCode = "404", description = "user not found")
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
    otp.setTempNom(request.getNom());
    otp.setTempPrenom(request.getPrenom());
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


@Operation(summary = "Verify OTP")
@ApiResponse(responseCode = "200", description = "telephone verified successfully")
@ApiResponse(responseCode = "404", description = "telephone not found")
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



@Operation(summary = "Complete registration")
@ApiResponse(responseCode = "200", description = "user logged in successfully")
@ApiResponse(responseCode = "404", description = "user not found")
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
        if (request.getCodeSecret() == null || request.getCodeSecret().length() < 4) {
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
        user.setPassword(passwordEncoder.encode(request.getCodeSecret()));
        user.setVerified(true);  // ← Déjà vérifié via OTP !
        user.setNom(otp.getTempNom());
        user.setPrenom(otp.getTempPrenom());
        user.setRole(Role.ROLE_USER);
        user.setLatitude(Double.parseDouble(otp.getLatitude()));
        user.setLongitude(Double.parseDouble(otp.getLongitude()));

        
        user.setBirthDate(otp.getDateNaissance().toString());
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



@Operation(summary = "Resend OTP")
@ApiResponse(responseCode = "200", description = "envoies otp successfully")
@ApiResponse(responseCode = "404", description = "failed to send otp")
@PostMapping("/resend-otp/{otpId}")
public ResponseEntity<?> resendOtp(@PathVariable Long otpId) {
    try {
        // 1. Récupérer l'ancien OTP
        Otp oldOtp = otpRepository.findById(otpId)
            .orElseThrow(() -> new NotFoundException("OTP non trouvé"));
        
        // 2. Vérifier qu'il n'est pas déjà utilisé
        if (oldOtp.isUsed()) {
            return ResponseEntity.badRequest()
                .body(ApiErrorResponse.validationError(
                    "Ce code a déjà été utilisé pour créer un compte"
                ));
        }
        
        // 3. Générer un NOUVEAU code
        smsService.sendOtpSms(oldOtp.getPhoneNumber(), generateOtp());
        
        return ResponseEntity.ok(ApiSuccessResponse.success(
            "Code OTP envoyé avec succès"
        ));
        
    } catch (NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiErrorResponse.notFound(e.getMessage()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiErrorResponse.internalServerError(
                "Erreur lors de l'envoi du code"
            ));
    }
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

}
        
