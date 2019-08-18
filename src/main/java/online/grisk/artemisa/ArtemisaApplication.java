package online.grisk.artemisa;

import online.grisk.artemisa.domain.pojo.Microservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.UUID;

@SpringBootApplication
@ImportResource("classpath:integration.cfg.xml")
public class ArtemisaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtemisaApplication.class, args);
    }

    /*@LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofHours(1))
                .setReadTimeout(Duration.ofHours(1))
                .build();
    }*/
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        /*RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);*/
        return new RestTemplate();
    }

    @Bean
    UUID getUUID() {
        return UUID.randomUUID();
    }

    @Value("${HERMES_USER}")
    String hermesUser;

    @Value("${HERMES_PASS}")
    String hermesPass;

    @Bean
    Microservice microserviceHermes() {
        return new Microservice("hermes", HttpMethod.POST, "/api/hermes", hermesUser, hermesPass, new HashMap<>());
    }

    @Value("${ATENEA_USER}")
    String ateneaUser;

    @Value("${ATENEA_PASS}")
    String ateneaPass;

    @Bean
    Microservice microserviceAtenea() {
        return new Microservice("atenea", HttpMethod.POST, "/api/atenea/report", ateneaUser, ateneaPass, new HashMap<>());
    }

    @Value("${HADES_USER}")
    String hadesUser;

    @Value("${HADES_PASS}")
    String hadesPass;

    @Bean
    Microservice microserviceHades() {
        return new Microservice("hades", HttpMethod.POST, "/api/atenea/ratios", hadesUser, hadesPass, new HashMap<>());
    }

    @Value("${POSEIDON_USER}")
    String poseidonUser;

    @Value("${POSEIDON_PASS}")
    String poseidonPass;

    @Bean
    Microservice microservicePoseidon() {
        return new Microservice("poseidon", HttpMethod.POST, "/api/poseidon/score", poseidonUser, poseidonPass, new HashMap<>());
    }

    @Value("${ZEUS_USER}")
    String zeusUser;

    @Value("${ZEUS_PASS}")
    String zeusPass;

    @Bean
    Microservice microserviceZeus() {
        return new Microservice("zeus", HttpMethod.POST, "/api/zeus/businessTree", zeusUser, zeusPass, new HashMap<>());
    }
}
