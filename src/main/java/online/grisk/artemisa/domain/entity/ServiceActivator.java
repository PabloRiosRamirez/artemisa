package online.grisk.artemisa.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
@AllArgsConstructor
public class ServiceActivator {

    private String serviceId;
private HttpMethod serviceCallMethod;

    private String servicePath;

    private String serviceUsername;

    private String servicePassword;

    private Map<String, Object> serviceHeaders;


    public String getUri() {
        return String.format("http://%s%s", this.serviceId, this.servicePath);
    }
}
