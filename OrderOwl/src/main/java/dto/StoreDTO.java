package dto;

import java.time.LocalDateTime;
import java.util.List;

public class StoreDTO {
	 private int storeId;
	    private int ownerId;
	    private String storeName;
	    private String address;
	    private String region;
	    private String phoneNumber;
	    private String description;
	    private String imgSrc;
	    private LocalDateTime createdAt;
	    List<MenuDTO> menu;
		public StoreDTO(int storeId, int ownerId, String storeName, String address, String region, String phoneNumber,
				String description, String imgSrc, LocalDateTime createdAt, List<MenuDTO> menu) {
			super();
			this.storeId = storeId;
			this.ownerId = ownerId;
			this.storeName = storeName;
			this.address = address;
			this.region = region;
			this.phoneNumber = phoneNumber;
			this.description = description;
			this.imgSrc = imgSrc;
			this.createdAt = createdAt;
			this.menu = menu;
		}
		
		public StoreDTO(int storeId, String storeName, String address, String region, String phoneNumber,
				String description, String imgSrc) {
			super();
			this.storeId = storeId;
			this.storeName = storeName;
			this.address = address;
			this.region = region;
			this.phoneNumber = phoneNumber;
			this.description = description;
			this.imgSrc = imgSrc;
		}
		
		public StoreDTO(int storeId, int ownerId, String storeName, String address, String region, String phoneNumber,
				String description, String imgSrc, LocalDateTime createdAt) {
			super();
			this.storeId = storeId;
			this.ownerId = ownerId;
			this.storeName = storeName;
			this.address = address;
			this.region = region;
			this.phoneNumber = phoneNumber;
			this.description = description;
			this.imgSrc = imgSrc;
			this.createdAt = createdAt;
		}
		public int getStoreId() {
			return storeId;
		}
		public void setStoreId(int storeId) {
			this.storeId = storeId;
		}
		public int getOwnerId() {
			return ownerId;
		}
		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}
		public String getStoreName() {
			return storeName;
		}
		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getImgSrc() {
			return imgSrc;
		}
		public void setImgSrc(String imgSrc) {
			this.imgSrc = imgSrc;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public List<MenuDTO> getMenu() {
			return menu;
		}
		public void setMenu(List<MenuDTO> menu) {
			this.menu = menu;
		}
		@Override
		public String toString() {
			return "StoreDTO [storeId=" + storeId + ", ownerId=" + ownerId + ", storeName=" + storeName + ", address="
					+ address + ", region=" + region + ", phoneNumber=" + phoneNumber + ", description=" + description
					+ ", imgSrc=" + imgSrc + ", createdAt=" + createdAt + ", menu=" + menu + "]";
		}
	    
	    
}
