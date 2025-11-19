// package heritage.africa.go_gainde_service.config;

// import java.security.Key;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

// @Component
// public class JwtTokenUtil {

//     private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

//     @Value("${jwt.expiration.ms}")
//     private long expirationMs;

//     public String generateToken(UserDetails userDetails) {
//         Map<String, Object> claims = new HashMap<>();
//         return Jwts.builder()
//                 .setClaims(claims)
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
//                 .signWith(key, SignatureAlgorithm.HS512)
//                 .compact();
//     }

//     public String getUsernameFromToken(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     public String getEmailFromToken(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     public boolean validateToken(String token, UserDetails userDetails) {
//         final String username = getUsernameFromToken(token);
//         return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         Date expiration = Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getExpiration();

//         return expiration.before(new Date());
//     }
// }








package heritage.africa.go_gainde_service.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import heritage.africa.go_gainde_service.entity.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration.ms}")
    private long expirationMs;

    // Génération du token avec username ET email dans les claims
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // Si votre UserDetails est en fait votre entité User
        if (userDetails instanceof Utilisateur) {
            Utilisateur user = (Utilisateur) userDetails;
            claims.put("email", user.getEmail());
            claims.put("username", user.getUsername());
            // Le subject sera le username par défaut
            return doGenerateToken(claims, user.getUsername());
        }
        
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Génération du token avec un identifiant personnalisé (pour supporter email OU username)
    public String generateTokenWithIdentifier(UserDetails userDetails, String identifier) {
        Map<String, Object> claims = new HashMap<>();
        
        if (userDetails instanceof Utilisateur) {
            Utilisateur Utilisateur = (Utilisateur) userDetails;
            claims.put("email", Utilisateur.getEmail());
            claims.put("username", Utilisateur.getUsername());
        }
        
        // Le subject sera l'identifiant fourni (email pour web, username pour mobile)
        return doGenerateToken(claims, identifier);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Extraire le subject (peut être username ou email selon le client)
    public String getIdentifierFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Extraire le username depuis le subject
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Extraire l'email depuis les claims personnalisés
    public String getEmailFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        Object email = claims.get("email");
        return email != null ? email.toString() : null;
    }

    // Extraire le username depuis les claims personnalisés
    public String getUsernameFromClaims(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        Object username = claims.get("username");
        return username != null ? username.toString() : null;
    }

    // Validation flexible : vérifie si le subject correspond au username OU à l'email
    public boolean validateToken(String token, UserDetails userDetails) {
        final String identifier = getIdentifierFromToken(token);
        
        // Vérifier si l'identifiant correspond au username
        boolean matchesUsername = identifier.equals(userDetails.getUsername());
        
        // Si votre UserDetails est un User, vérifier aussi l'email
        boolean matchesEmail = false;
        if (userDetails instanceof Utilisateur) {
            Utilisateur user = (Utilisateur) userDetails;
            matchesEmail = identifier.equals(user.getEmail());
        }
        
        return (matchesUsername || matchesEmail) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}