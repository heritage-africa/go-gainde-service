package heritage.africa.go_gainde_service.service;

public interface SmsService {

    void sendOtpSms(String phoneNumber, String otpCode);

}
