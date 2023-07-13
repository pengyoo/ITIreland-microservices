package works.itireland.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificationApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(NotificationApp.class, args);
    }
}
