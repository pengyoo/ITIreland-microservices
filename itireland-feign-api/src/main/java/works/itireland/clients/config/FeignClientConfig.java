package works.itireland.clients.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor browserHeaderInterceptor() {
        return new BrowserHeaderInterceptor();
    }
}
