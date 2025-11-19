package heritage.africa.go_gainde_service.core;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Classe utilitaire générique pour construire des réponses d'erreur standardisées.
 * Peut être réutilisée dans n'importe quel projet Spring Boot.
 */
public class ApiErrorResponse {
    
    // ==================== Erreurs Génériques ====================
    
    public static Map<String, Object> badRequest(String message) {
        return buildError(HttpStatus.BAD_REQUEST, "BAD_REQUEST", message);
    }
    
    public static Map<String, Object> notFound(String resource) {
        return buildError(
            HttpStatus.NOT_FOUND, 
            "NOT_FOUND", 
            resource + " not found"
        );
    }
    
    public static Map<String, Object> unauthorized(String message) {
        return buildError(
            HttpStatus.UNAUTHORIZED, 
            "UNAUTHORIZED", 
            message != null ? message : "Authentication required"
        );
    }
    
    public static Map<String, Object> forbidden(String message) {
        return buildError(
            HttpStatus.FORBIDDEN, 
            "FORBIDDEN", 
            message != null ? message : "Access denied"
        );
    }
    
    public static Map<String, Object> conflict(String message) {
        return buildError(
            HttpStatus.CONFLICT, 
            "CONFLICT", 
            message
        );
    }
    
    public static Map<String, Object> internalServerError(String message) {
        return buildError(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            "INTERNAL_SERVER_ERROR", 
            message != null ? message : "An internal error occurred"
        );
    }
    
    public static Map<String, Object> serviceUnavailable(String message) {
        return buildError(
            HttpStatus.SERVICE_UNAVAILABLE, 
            "SERVICE_UNAVAILABLE", 
            message != null ? message : "Service temporarily unavailable"
        );
    }
    
    public static Map<String, Object> tooManyRequests(String message) {
        return buildError(
            HttpStatus.TOO_MANY_REQUESTS, 
            "TOO_MANY_REQUESTS", 
            message != null ? message : "Rate limit exceeded"
        );
    }
    
    // ==================== Erreurs de Validation ====================
    
    public static Map<String, Object> validationError(String message) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "VALIDATION_ERROR", 
            message
        );
    }
    
    public static Map<String, Object> missingField(String fieldName) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "MISSING_FIELD", 
            "Field '" + fieldName + "' is required"
        );
    }
    
    public static Map<String, Object> invalidField(String fieldName, String reason) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "INVALID_FIELD", 
            "Field '" + fieldName + "' is invalid: " + reason
        );
    }
    
    public static Map<String, Object> invalidFormat(String message) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "INVALID_FORMAT", 
            message
        );
    }
    
    // ==================== Erreurs Métier ====================
    
    public static Map<String, Object> businessError(String code, String message) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            code, 
            message
        );
    }
    
    public static Map<String, Object> insufficientBalance(String message) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "INSUFFICIENT_BALANCE", 
            message != null ? message : "Insufficient balance"
        );
    }
    
    public static Map<String, Object> operationNotAllowed(String message) {
        return buildError(
            HttpStatus.FORBIDDEN, 
            "OPERATION_NOT_ALLOWED", 
            message
        );
    }
    
    public static Map<String, Object> limitExceeded(String message) {
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "LIMIT_EXCEEDED", 
            message
        );
    }
    
    public static Map<String, Object> resourceAlreadyExists(String resource) {
        return buildError(
            HttpStatus.CONFLICT, 
            "ALREADY_EXISTS", 
            resource + " already exists"
        );
    }
    
    public static Map<String, Object> resourceExpired(String resource) {
        return buildError(
            HttpStatus.GONE, 
            "EXPIRED", 
            resource + " has expired"
        );
    }
    
    // ==================== Méthode de Construction ====================
    
    /**
     * Construit une réponse d'erreur standardisée
     * 
     * @param status Code de statut HTTP
     * @param code Code d'erreur métier
     * @param message Message descriptif de l'erreur
     * @return Map contenant les détails de l'erreur
     */
    public static Map<String, Object> buildError(HttpStatus status, String code, String message) {
        Map<String, Object> error = new HashMap<>();
        Map<String, Object> errorDetails = new HashMap<>();
        
        errorDetails.put("code", code);
        errorDetails.put("message", message);
        errorDetails.put("timestamp", LocalDateTime.now());
        
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("details", errorDetails);
        
        return error;
    }
    
    /**
     * Construit une réponse d'erreur avec des données supplémentaires
     * 
     * @param status Code de statut HTTP
     * @param code Code d'erreur métier
     * @param message Message descriptif de l'erreur
     * @param additionalData Données supplémentaires à inclure
     * @return Map contenant les détails de l'erreur
     */
    public static Map<String, Object> buildError(HttpStatus status, String code, String message, Map<String, Object> additionalData) {
        Map<String, Object> error = buildError(status, code, message);
        
        if (additionalData != null && !additionalData.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> details = (Map<String, Object>) error.get("details");
            details.putAll(additionalData);
        }
        
        return error;
    }
    
    /**
     * Construit une erreur personnalisée avec tous les paramètres
     */
    public static Map<String, Object> custom(HttpStatus status, String code, String message) {
        return buildError(status, code, message);
    }
    
    /**
     * Construit une erreur personnalisée avec données additionnelles
     */
    public static Map<String, Object> custom(HttpStatus status, String code, String message, Map<String, Object> additionalData) {
        return buildError(status, code, message, additionalData);
    }
    
    // ==================== Erreurs avec Validation Multiple ====================
    
    /**
     * Pour gérer plusieurs erreurs de validation en même temps
     */
    public static Map<String, Object> multipleValidationErrors(Map<String, String> fieldErrors) {
        Map<String, Object> additionalData = new HashMap<>();
        additionalData.put("fields", fieldErrors);
        
        return buildError(
            HttpStatus.BAD_REQUEST, 
            "VALIDATION_ERROR", 
            "Multiple validation errors occurred",
            additionalData
        );
    }
}