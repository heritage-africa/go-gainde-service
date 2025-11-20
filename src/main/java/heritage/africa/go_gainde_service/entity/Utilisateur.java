package heritage.africa.go_gainde_service.entity;

import heritage.africa.go_gainde_service.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Utilisateur  extends AbstracType {


    @Column(nullable = false)
    private String username;


    @Column(nullable = true)
    private String nom;


    @Column(nullable = true)
    private String prenom;


    @Column(nullable = true)
    private  String localisation;


    @Column(nullable = true)
    private int codeSecret;


    @Column(nullable = false)
    private String password;




    @Column(nullable = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    //date de naissance
    @Column(name = "birth_date", nullable = true)
    private String birthDate;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    // New fields for storing longitude and latitude
    @Column(name = "longitude", nullable = true)
    private Double longitude;

   

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // private List<Otp> otps = new ArrayList<>();



}
