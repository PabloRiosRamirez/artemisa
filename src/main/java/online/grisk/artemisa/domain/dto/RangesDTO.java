package online.grisk.artemisa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RangesDTO {

    Short limitDown;
    Short limitUp;
    String color;

    public Map<String, Object> toMap() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("limitDown", limitDown);
        objectMap.put("limitUp", limitUp);
        objectMap.put("color", color);
        return objectMap;
    }
}
