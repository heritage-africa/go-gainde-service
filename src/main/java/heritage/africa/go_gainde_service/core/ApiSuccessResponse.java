package heritage.africa.go_gainde_service.core;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Classe utilitaire générique pour construire des réponses de succès standardisées.
 * Peut être réutilisée dans n'importe quel projet Spring Boot.
 */
public class ApiSuccessResponse {
    
    /**
     * Réponse de succès simple avec données
     */
    public static Map<String, Object> success(Object data) {
        return buildResponse(HttpStatus.OK, "Success", data);
    }
    
    /**
     * Réponse de succès avec message personnalisé
     */
    public static Map<String, Object> success(String message, Object data) {
        return buildResponse(HttpStatus.OK, message, data);
    }
    
    /**
     * Réponse de création réussie (201)
     */
    public static Map<String, Object> created(Object data) {
        return buildResponse(HttpStatus.CREATED, "Resource created successfully", data);
    }
    
    /**
     * Réponse de création avec message personnalisé
     */
    public static Map<String, Object> created(String message, Object data) {
        return buildResponse(HttpStatus.CREATED, message, data);
    }
    
    /**
     * Réponse de mise à jour réussie
     */
    public static Map<String, Object> updated(Object data) {
        return buildResponse(HttpStatus.OK, "Resource updated successfully", data);
    }
    
    /**
     * Réponse de mise à jour avec message personnalisé
     */
    public static Map<String, Object> updated(String message, Object data) {
        return buildResponse(HttpStatus.OK, message, data);
    }
    
    /**
     * Réponse de suppression réussie
     */
    public static Map<String, Object> deleted() {
        return buildResponse(HttpStatus.OK, "Resource deleted successfully", null);
    }
    
    /**
     * Réponse de suppression avec message personnalisé
     */
    public static Map<String, Object> deleted(String message) {
        return buildResponse(HttpStatus.OK, message, null);
    }
    
    /**
     * Réponse sans contenu (204)
     */
    public static Map<String, Object> noContent() {
        return buildResponse(HttpStatus.NO_CONTENT, "No content", null);
    }
    
    /**
     * Réponse acceptée (202) - pour opérations asynchrones
     */
    public static Map<String, Object> accepted(String message) {
        return buildResponse(HttpStatus.ACCEPTED, message, null);
    }
    
    /**
     * Réponse acceptée avec données
     */
    public static Map<String, Object> accepted(String message, Object data) {
        return buildResponse(HttpStatus.ACCEPTED, message, data);
    }
    
    /**
     * Construit une réponse de succès standardisée
     * 
     * @param status Code de statut HTTP
     * @param message Message de succès
     * @param data Données à retourner
     * @return Map contenant la réponse
     */
    public static Map<String, Object> buildResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", status.value());
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        
        if (data != null) {
            response.put("data", data);
        }
        
        return response;
    }
    
    /**
     * Construit une réponse avec métadonnées supplémentaires
     */
    public static Map<String, Object> buildResponse(HttpStatus status, String message, Object data, Map<String, Object> metadata) {
        Map<String, Object> response = buildResponse(status, message, data);
        
        if (metadata != null && !metadata.isEmpty()) {
            response.put("metadata", metadata);
        }
        
        return response;
    }
    
    /**
     * Réponse paginée
     */
    public static Map<String, Object> paginated(Object data, int page, int size, long totalElements, int totalPages) {
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("page", page);
        pagination.put("size", size);
        pagination.put("totalElements", totalElements);
        pagination.put("totalPages", totalPages);
        
        return buildResponse(HttpStatus.OK, "Success", data, pagination);
    }
    
    /**
     * Réponse personnalisée
     */
    public static Map<String, Object> custom(HttpStatus status, String message, Object data) {
        return buildResponse(status, message, data);
    }
}