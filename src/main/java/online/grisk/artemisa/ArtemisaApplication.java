package online.grisk.artemisa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("integration.cfg.xml")
public class ArtemisaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtemisaApplication.class, args);
    }

}
