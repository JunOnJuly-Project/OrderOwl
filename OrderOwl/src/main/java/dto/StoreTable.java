package dto;

import java.util.List;

public class StoreTable {
	private int tableId;
    private int storeId;
    private String tableNo;
    String qrSrc;
	public StoreTable(int tableId, int storeId, String tableNo, String qrSrc) {
		super();
		this.tableId = tableId;
		this.storeId = storeId;
		this.tableNo = tableNo;
		this.qrSrc = qrSrc;
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
	public String getQrSrc() {
		return qrSrc;
	}
	public void setQrSrc(String qrSrc) {
		this.qrSrc = qrSrc;
	}
	@Override
	public String toString() {
		return "StoreTable [tableId=" + tableId + ", storeId=" + storeId + ", tableNo=" + tableNo + ", qrSrc=" + qrSrc
				+ "]";
	}
    
    
    
    
}
