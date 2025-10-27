package dto;

import java.time.LocalDateTime;

public class QrcodeDTO {
	 private int qrcodeId;
    private int tableId;
    private String qrcodeData;
    private String qrImgSrc;
    private LocalDateTime createdAt;
    
	public QrcodeDTO(int qrcodeId, int tableId, String qrcodeData, String qrImgSrc, LocalDateTime createdAt) {
		super();
		this.qrcodeId = qrcodeId;
		this.tableId = tableId;
		this.qrcodeData = qrcodeData;
		this.qrImgSrc = qrImgSrc;
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "QrcodeDTO [qrcodeId=" + qrcodeId + ", tableId=" + tableId + ", qrcodeData=" + qrcodeData + ", qrImgSrc="
				+ qrImgSrc + ", createdAt=" + createdAt + "]";
	}

	public int getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(int qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public String getQrcodeData() {
		return qrcodeData;
	}

	public void setQrcodeData(String qrcodeData) {
		this.qrcodeData = qrcodeData;
	}

	public String getQrImgSrc() {
		return qrImgSrc;
	}

	public void setQrImgSrc(String qrImgSrc) {
		this.qrImgSrc = qrImgSrc;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	

}

