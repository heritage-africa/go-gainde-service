package heritage.africa.go_gainde_service.mobile.dto.Request;


import lombok.Data;

@Data
public class SendOtpRequest {
    
    private String username;
    private String dateNaissance;
    private String phoneNumber;
    private String longitude;
    private String latitude;
}
