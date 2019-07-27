package online.grisk.artemisa;

import online.grisk.artemisa.domain.entity.ServiceActivator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

@SpringBootApplication
@ImportResource("classpath:integration.cfg.xml")
public class ArtemisaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtemisaApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofHours(1))
                .setReadTimeout(Duration.ofHours(1))
                .build();
    }

    @Bean
    UUID getUUID() {
        return UUID.randomUUID();
    }

    @Bean
    ServiceActivator serviceActivatorHermes() {
        return new ServiceActivator("hermes", HttpMethod.POST, "/api/hermes", "hermes", "GRisk.2019", new HashMap<>());
    }

    @Bean
    ServiceActivator serviceActivatorAtenea() {
        return new ServiceActivator("atenea", HttpMethod.POST, "/api/atenea", "atenea", "GRisk.2019", new HashMap<>());
    }
}
