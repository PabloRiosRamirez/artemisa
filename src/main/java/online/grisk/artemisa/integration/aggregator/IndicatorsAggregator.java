package online.grisk.artemisa.integration.aggregator;

import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IndicatorsAggregator extends AbstractAggregatingMessageGroupProcessor {

    @Override
    protected Object aggregatePayloads(MessageGroup messageGroup, Map<String, Object> map) {
        Map<String, Object> response = new HashMap<>();
        response.put("dataintegration", ((Map) messageGroup.getOne().getPayload()).get("dataintegration"));
        for (Message<?> message : messageGroup.getMessages()) {
            presentValues(response, (Map<String, Object>) message.getPayload(), "riskScore");
            presentValues(response, (Map<String, Object>) message.getPayload(), "riskRatios");
            presentValues(response, (Map<String, Object>) message.getPayload(), "businessTree");
        }
        return response;
    }

    private void presentValues(Map<String, Object> response, Map<String, Object> payload, String section) {
        if (((Map<String, Object>) payload.get(section)).get("values") != null) {
            response.put(section, payload.get(section));
        }
    }
}
