package works.itireland.notification;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootTest
public class EmailTest {
//    @Autowired
//    private MailSender mailSender;
//
//    @Test
//    public void testEmail(){
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("itireland8@gmail.com");
//        simpleMailMessage.setTo("yp1990@gmail.com");
//        simpleMailMessage.setSubject("Test Email");
//        simpleMailMessage.setText("Hello, Peng");
//        mailSender.send(simpleMailMessage);
//    }
}
