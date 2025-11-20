package heritage.africa.go_gainde_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class AbstracType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    //createAt, updateAt


    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
