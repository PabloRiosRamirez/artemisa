package online.grisk.artemisa.domain.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTreeDTO {

	private long organization;
	private String titule;
	Collection<NodeTreeDTO> nodes;

	public Map<String, Object> toMap() {
		Map<String, Object> objectMap = new HashMap<>();
		objectMap.put("organization", organization);
		objectMap.put("titule", titule);
		objectMap.put("nodes", nodes);
		return objectMap;
	}
}
