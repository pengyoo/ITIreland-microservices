package works.itireland.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication(scanBasePackages =
        {
                "works.itireland.amqp",
                "works.itireland.notification",
                "works.itireland.auth"
        }
)
@EnableFeignClients(basePackages = "works.itireland.clients")
public class NotificationApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(NotificationApp.class, args);
    }

}
