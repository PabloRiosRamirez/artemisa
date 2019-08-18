package online.grisk.artemisa.domain.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskRatioRatioDTO {

	private String titule;
	private String color;
	private String postResult;
	private String expression;
	private short orderDisplay;

	public Map<String, Object> toMap() {
		Map<String, Object> objectMap = new HashMap<>();
		objectMap.put("titule", titule);
		objectMap.put("color", color);
		objectMap.put("postResult", postResult);
		objectMap.put("expression", expression);
		objectMap.put("orderDisplay", orderDisplay);
		return objectMap;
	}

}
