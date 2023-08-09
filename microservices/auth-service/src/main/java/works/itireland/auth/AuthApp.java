package works.itireland.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages = {
        "works.itireland.auth",
})
@EnableFeignClients(basePackages = "works.itireland.clients")
public class AuthApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthApp.class);
    }
}
