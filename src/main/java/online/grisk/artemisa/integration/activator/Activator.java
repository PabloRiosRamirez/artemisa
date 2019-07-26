package online.grisk.artemisa.integration.activator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface Activator {
    public ResponseEntity<?> invoke(@Payload Map<String, Object> payload, @Headers HttpHeaders headers);
}
