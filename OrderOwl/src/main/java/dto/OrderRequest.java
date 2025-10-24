package dto;

import java.util.List;

public class OrderRequest {
	
	String key;
	String methodName;
	List<MenuDTO> dto;
	
	
	
	public OrderRequest(String key, String methodName, List<MenuDTO> dto) {
		super();
		this.key = key;
		this.methodName = methodName;
		this.dto = dto;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<MenuDTO> getDto() {
		return dto;
	}
	public void setDto(List<MenuDTO> dto) {
		this.dto = dto;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderRequest [key=");
		builder.append(key);
		builder.append(", methodName=");
		builder.append(methodName);
		builder.append(", dto=");
		builder.append(dto);
		builder.append("]");
		return builder.toString();
	}
	
	

}
