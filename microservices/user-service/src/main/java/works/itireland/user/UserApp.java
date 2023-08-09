package works.itireland.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "works.itireland.user",
        "works.itireland.amqp",
        "works.itireland.auth"
})
@EnableFeignClients(basePackages = "works.itireland.clients")
public class UserApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserApp.class, args);
    }
}
