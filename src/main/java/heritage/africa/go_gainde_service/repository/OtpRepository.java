package heritage.africa.go_gainde_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import heritage.africa.go_gainde_service.entity.Otp;
import heritage.africa.go_gainde_service.entity.enums.OtpType;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

   Optional<Otp> findByCodeAndPhoneNumberAndTypeAndVerifiedIsFalseAndExpiresAtAfter(
    String code, 
    String phoneNumber, 
    OtpType type, 
    LocalDateTime now
);

    List<Otp> findByPhoneNumberAndVerifiedIsFalseAndExpiresAtBefore(String phoneNumber, LocalDateTime now);

}
