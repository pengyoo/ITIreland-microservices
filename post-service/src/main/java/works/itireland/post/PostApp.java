package works.itireland.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "works.itireland.amqp",
        "works.itireland.post",
        "works.itireland.auth",
        "works.itireland.exception",
})
@EnableFeignClients(basePackages = "works.itireland.clients")
public class PostApp {
    public static void main(String[] args) {
        SpringApplication.run(PostApp.class, args);
    }
}
