package heritage.africa.go_gainde_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Opponent extends AbstracType {

    private String name;


    private String logo;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

    // Getters & Setters
}
