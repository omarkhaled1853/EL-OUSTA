package com.ELOUSTA.ELOUSTA.backend.service.otp;

import com.ELOUSTA.ELOUSTA.backend.model.MailStructure;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final String fromMail = "eloustaofficial@gmail.com";
    Map<String,String> otpmap = new HashMap<String,String>();

    public MailService() {
        this.mailSender = createCustomMailSender();
    }

    public void sendAdminMail(String mail, MailStructure mailStructure,String username , String password)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText("Your are now admin in el Osta with username :"+username +" and password : "+password);
        mailSender.send(simpleMailMessage);
    }

    public void sendMail(String mail, MailStructure mailStructure) {
        String otp = generateOtp();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText("the verification number is : " + otp);
        mailSender.send(simpleMailMessage);
        otpmap.put(mail,otp);
        System.out.println(otpmap.toString());
    }
    public String generateOtp() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    public boolean verification(String mail,String inputotp) {
        return this.otpmap.get(mail).equals(inputotp);
    }

    private JavaMailSender createCustomMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("eloustaofficial@gmail.com");
        mailSender.setPassword("gmkjvhzjsvvddivs");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        // Trust all certificates for testing
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) { }
                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) { }
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            }, new java.security.SecureRandom());

            mailSender.getJavaMailProperties().put("mail.smtp.ssl.socketFactory", sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mailSender;
    }
}
