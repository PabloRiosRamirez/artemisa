package online.grisk.artemisa.domain.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTreeDTO {

	private String expression;
	private boolean output;
	private String labelOutput;
	private String color;
	private Long childrenNegation;
	private Long childrenAfirmation;

	public Map<String, Object> toMap() {
		Map<String, Object> objectMap = new HashMap<>();
		objectMap.put("expression", expression);
		objectMap.put("output", output);
		objectMap.put("labelOutput", labelOutput);
		objectMap.put("color", color);
		objectMap.put("childrenNegation", childrenNegation);
		objectMap.put("childrenAfirmation", childrenAfirmation);
		return objectMap;
	}
}
