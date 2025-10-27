package dto;

import java.util.List;

public class StoreTableDTO {
	private int tableId;
    private int storeId;
    private String tableNo;

	public StoreTableDTO(int tableId, int storeId, String tableNo) {
		super();
		this.tableId = tableId;
		this.storeId = storeId;
		this.tableNo = tableNo;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getTableNo() {
		return tableNo;
	}
	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	@Override
	public String toString() {
		return "StoreTable [tableId=" + tableId + ", storeId=" + storeId + ", tableNo=" + tableNo
				+ "]";
	}
    
    
    
    
}
