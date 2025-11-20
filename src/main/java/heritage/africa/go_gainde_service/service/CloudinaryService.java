package heritage.africa.go_gainde_service.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;


@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        // Configuration Cloudinary
        //douedjv5i
        //772192627612489
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "douedjv5i",
                "api_key", "772192627612489",
                "api_secret", "ikdbBaFCOaaVNvUVwnmUmtGvheI"
        ));
    }

    /**
     * Upload une image vers Cloudinary et retourne l'URL
     * @param imageFile le fichier image à uploader
     * @return l'URL de l'image uploadée
     */
    public String uploadImage(MultipartFile imageFile) {
        try {
            // Upload vers Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    imageFile.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "profile_photos", // Dossier pour organiser
                            "resource_type", "image",
                            "quality", "auto",
                            "fetch_format", "auto"
                    )
            );
            
            // Récupération de l'URL sécurisée
            return uploadResult.get("secure_url").toString();
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image: " + e.getMessage());
        }
    }

    /**
     * Upload une image depuis une URL et retourne la nouvelle URL Cloudinary
     * @param imageUrl l'URL de l'image à uploader
     * @return l'URL Cloudinary de l'image
     */
    public String uploadImageFromUrl(String imageUrl) {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    imageUrl,
                    ObjectUtils.asMap(
                            "folder", "profile_photos",
                            "resource_type", "image"
                    )
            );
            
            return uploadResult.get("secure_url").toString();
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload depuis URL: " + e.getMessage());
        }
    }

    /**
     * Récupère l'URL d'une image depuis Cloudinary par son public_id
     * @param publicId l'identifiant public de l'image
     * @return l'URL de l'image
     */
    public String getImageUrl(String publicId) {
        return cloudinary.url()
                .resourceType("image")
                .publicId(publicId)
                .secure(true)
                .generate();
    }

    /**
     * Récupère une URL avec transformation (redimensionnement, etc.)
     * @param publicId l'identifiant public de l'image
     * @param width largeur souhaitée
     * @param height hauteur souhaitée
     * @return l'URL transformée
     */
    public String getTransformedImageUrl(String publicId, int width, int height) {
        return cloudinary.url()
                .resourceType("image")
                .publicId(publicId)
                .transformation(new Transformation()
                        .width(width)
                        .height(height)
                        .crop("fill")
                        .gravity("auto"))
                .secure(true)
                .generate();
    }

    /**
     * Supprime une image de Cloudinary
     * @param publicId l'identifiant public de l'image à supprimer
     */
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression: " + e.getMessage());
        }
    }

/**
 * Extrait le public_id depuis une URL Cloudinary
 * @param cloudinaryUrl l'URL complète Cloudinary
 * @return le public_id (ex: "profile_photos/user123")
 */
public String extractPublicIdFromUrl(String cloudinaryUrl) {
    if (cloudinaryUrl == null || !cloudinaryUrl.contains("cloudinary.com")) {
        return null;
    }

    try {
        // Exemple : https://res.cloudinary.com/demo/image/upload/v1234567890/profile_photos/user123.jpg
        String[] parts = cloudinaryUrl.split("/upload/");
        if (parts.length < 2) return null;

        // Prend la partie après "upload/"
        String publicPath = parts[1];

        // Supprime la version si présente (ex: v1234567890)
        publicPath = publicPath.replaceFirst("v[0-9]+/", "");

        // Supprime l'extension (.jpg, .png, etc.)
        if (publicPath.contains(".")) {
            publicPath = publicPath.substring(0, publicPath.lastIndexOf('.'));
        }

        return publicPath;
    } catch (Exception e) {
        return null;
    }
}

}