package heritage.africa.go_gainde_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import heritage.africa.go_gainde_service.entity.enums.OtpType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Otp extends AbstracType {



    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean verified;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType type;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id", nullable = false)
    // private Utilisateur user;
    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String tempUsername;  // Stocke temporairement le username
    
    @Column
    private String tempPassword; 
    
    
       @Column(nullable = false)
    private boolean used = false;  // 

    @Column
    private String Longitude;

    @Column
    private String Latitude;

    @Column
    private LocalDate dateNaissance;


    @Column
    private String tempNom;

    @Column
    private String tempPrenom;

    

}
