package heritage.africa.go_gainde_service.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Match extends AbstracType {

    private LocalDateTime date;
    private String time;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Stade stadium;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Opponent opponent;
    private boolean isHome;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Competition competition;


    public String getDateFormatted() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return date.format(formatter);
}

    // ... list injured
}